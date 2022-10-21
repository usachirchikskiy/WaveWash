package com.example.wavewash.presentation.services.update_service

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.presentation.services.new_service.NewServiceEvent
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "UpdateServiceViewModel"

@HiltViewModel
class UpdateServiceViewModel @Inject
constructor(
    private val service: Service,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(UpdateServiceState())

    private var job: Job? = null

    init {
        savedStateHandle.get<Long>("serviceId")?.let { serviceId ->
            onTriggerEvent(UpdateServiceEvent.GetService(serviceId))
        }
    }

    fun onTriggerEvent(event: UpdateServiceEvent) {
        when (event) {
            is UpdateServiceEvent.Back -> {
                changeCompletedValue()
            }

            is UpdateServiceEvent.UpdateService -> {
                val fieldsIsEmtpy = checkFields()
                if (fieldsIsEmtpy) {
//                    TODO POPUP DIALOG
                } else {
                    updateService()
                }
            }
            is UpdateServiceEvent.GetService -> {
                getService(event.id)
            }
            is UpdateServiceEvent.ChangeNameValue -> {
                changeNameValue(event.name)
            }
            is UpdateServiceEvent.ChangeDurationValue -> {
                changeDurationValue(event.duration)
            }
            is UpdateServiceEvent.ChangePriceValue -> {
                changePriceValue(event.price)
            }
        }
    }

    private fun checkFields(): Boolean {
        if (state.price.isEmpty() || state.duration.isEmpty()
            || state.name.isEmpty()
        ) {
            return true
        }
        return false
    }

    private fun changeCompletedValue() {
        state = state.copy(
            changeCompleted = false
        )
    }

    private fun updateService() {
        val body = ServiceDto(state.duration.toInt(), state.name, state.price.toInt())
        job?.cancel()
        job = service.update(body, state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false, changeCompleted = true)
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

    private fun getService(id: Long) {
        job?.cancel()
        job = service.get_service(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        id = result.data!!.id,
                        name = result.data.name,
                        price = result.data.price.toString(),
                        duration = result.data.duration.toString()
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

    private fun changePriceValue(price: String) {
        state = state.copy(price = price)
    }

    private fun changeNameValue(name: String) {
        state = state.copy(name = name)
    }

    private fun changeDurationValue(duration: String) {
        state = state.copy(duration = duration)
    }
}