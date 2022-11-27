package com.example.wavewash.presentation.janitors.update_janitor

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.washer.AddWasherDto
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorName
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorStake
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorTelephone
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.asFile
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

private const val TAG = "UpdateJanitorViewModel"

@HiltViewModel
class UpdateJanitorViewModel @Inject
constructor(
    private val washerUseCase: WasherUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val validationJanitorTelephone: ValidationJanitorTelephone,
    private val validationJanitorName: ValidationJanitorName,
    private val validationJanitorStake: ValidationJanitorStake,
    private val application: Application
) : ViewModel() {

    var state by mutableStateOf(UpdateJanitorState())
    private var job: Job? = null
    private val gson = Gson()

    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Long>("washerId")?.let { washerId ->
            onTriggerEvent(UpdateJanitorEvents.GetWasher(washerId))
        }
    }

    fun onTriggerEvent(event: UpdateJanitorEvents) {
        when (event) {
            is UpdateJanitorEvents.UpdateWasher -> {
                updateWasher()
            }
            is UpdateJanitorEvents.GetWasher -> {
                getWasher(event.id)
            }
            is UpdateJanitorEvents.ChangeNameValue -> {
                changeNameValue(event.text)
            }
            is UpdateJanitorEvents.ChangeStakeValue -> {
                changeStakeValue(event.stake)
            }
            is UpdateJanitorEvents.ChangePhoneNumberValue -> {
                changePhoneNumberValue(event.phoneNumber)
            }
            is UpdateJanitorEvents.GalleryImage -> {
                changeUriValue(event.uri)
            }
        }
    }

    private fun getWasher(id: Long) {
        job?.cancel()
        job = washerUseCase.get_washer_not_flow(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        id = result.data!!.id,
                        telephoneNumber = result.data.telephoneNumber,
                        stake = result.data.stake.toString(),
                        name = result.data.name,
                        imageUrl = result.data.image
                    )
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateWasher() {

        val nameResult = validationJanitorName.execute(state.name)
        val stakeResult = validationJanitorStake.execute(state.stake)
        val telephoneNumberResult = validationJanitorTelephone.execute(state.telephoneNumber)

        val hasError = listOf(
            nameResult,
            stakeResult,
            telephoneNumberResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                stakeError = stakeResult.errorMessage,
                telephoneNumberError = telephoneNumberResult.errorMessage,
            )
            return
        }

        job?.cancel()
        val addWasherDto =
            AddWasherDto(state.name, state.stake.toInt(), state.telephoneNumber.toInt())
        val body =
            RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                gson.toJson(addWasherDto)
            )
        val data = MultipartBody.Part
            .createFormData(
                "data",
                "",
                body
            )

        var multipartBody: MultipartBody.Part? = null
        state.uri?.let { uri ->
            val imageFile = uri.asFile(application)
            if (imageFile != null) {
                if (imageFile.exists()) {
                    val requestBody =
                        RequestBody.create(
                            "application/octet-stream".toMediaTypeOrNull(),
                            imageFile
                        )
                    multipartBody = MultipartBody.Part.createFormData(
                        "file",
                        imageFile.name,
                        requestBody
                    )
                }
            }
        }
        job = washerUseCase.update(data, multipartBody, state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(NavigationEvent.GoBack)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeStakeValue(stake: String) {
        state = state.copy(stake = stake)
    }

    private fun changeNameValue(name: String) {
        state = state.copy(name = name)
    }

    private fun changePhoneNumberValue(telephoneNumber: String) {
        state = state.copy(telephoneNumber = telephoneNumber)
    }

    private fun changeUriValue(uri: Uri) {
        state = state.copy(uri = uri)
    }

}