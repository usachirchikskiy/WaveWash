package com.example.wavewash.presentation.janitors.update_janitor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.washer.WasherDto
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "UpdateJanitorViewModel"

@HiltViewModel
class UpdateJanitorViewModel @Inject
constructor(
    private val washer: Washer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(UpdateJanitorState())
    private var job: Job? = null

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
            is UpdateJanitorEvents.ChangeNameValue ->{
                changeNameValue(event.text)
            }
            is UpdateJanitorEvents.ChangeStakeValue ->{
                changeStakeValue(event.stake)
            }
            is UpdateJanitorEvents.ChangePhoneNumberValue -> {
                changePhoneNumberValue(event.phoneNumber)
            }
        }
    }

    private fun getWasher(id: Long) {
        job?.cancel()
        job = washer.get_washer(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        id = result.data!!.id,
                        telephoneNumber = result.data.telephoneNumber.toString(),
                        stake = result.data.stake.toString(),
                        name = result.data.name
                    )
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, isLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateWasher() {
        val body = WasherDto(state.name, state.stake.toInt(), state.telephoneNumber.toInt())
        job?.cancel()
        job = washer.update(body,state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                  state = state.copy(isLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!,isLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeStakeValue(stake: Int) {
        state = state.copy(stake = stake.toString())
    }

    private fun changeNameValue(name: String) {
        state = state.copy(name = name)
    }

    private fun changePhoneNumberValue(telephoneNumber: Int) {
        state = state.copy(telephoneNumber = telephoneNumber.toString())
    }

}