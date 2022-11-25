package com.example.wavewash.presentation.janitors.new_janitor

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.washer.AddWasherDto
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.domain.validation_use_case.ValidationJanitorName
import com.example.wavewash.domain.validation_use_case.ValidationJanitorStake
import com.example.wavewash.domain.validation_use_case.ValidationJanitorTelephone
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
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


private const val TAG = "NewJanitorViewModel"

@HiltViewModel
class NewJanitorViewModel @Inject
constructor(
    private val washerUseCase: WasherUseCase,
    private val validationJanitorTelephone: ValidationJanitorTelephone,
    private val validationJanitorName: ValidationJanitorName,
    private val validationJanitorStake: ValidationJanitorStake,
    private val application: Application
) : ViewModel() {

    var state by mutableStateOf(NewJanitorState())
    private var job: Job? = null
    private val gson = Gson()

    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onTriggerEvent(event: NewJanitorEvents) {
        when (event) {
            is NewJanitorEvents.AddWasher -> {
                addWasher()
            }
            is NewJanitorEvents.ChangeStakeValue -> {
                changeStakeValue(event.stake)
            }
            is NewJanitorEvents.ChangeNameValue -> {
                changeNameValue(event.text)
            }
            is NewJanitorEvents.ChangePhoneNumberValue -> {
                changePhoneNumberValue(event.phoneNumber)
            }
            is NewJanitorEvents.GalleryImage -> {
                changeUri(event.uri)
            }
        }
    }

    private fun changeUri(uri: Uri) {
        state = state.copy(uri = uri)
    }

    private fun addWasher() {
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
        state.uri?.let{ uri ->
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
        job = washerUseCase.addWasher(data, multipartBody).onEach { result ->
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

//    private fun checkFields(): Boolean {
//        if (state.telephoneNumber.isEmpty() || state.stake.isEmpty()
//            || state.name.isEmpty()
//        ) {
//            return true
//        }
//        return false
//    }


}

