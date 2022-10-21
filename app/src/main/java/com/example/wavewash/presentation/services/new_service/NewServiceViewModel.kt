package com.example.wavewash.presentation.services.new_service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.presentation.orders.new_order.NewOrderEvent
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewServiceViewModel @Inject
constructor(
    private val service: Service
) : ViewModel() {

    var state by mutableStateOf(NewServiceState())
    private var job: Job? = null

    fun onTriggerEvent(event: NewServiceEvent) {
        when (event) {
            is NewServiceEvent.Back -> {
                changeCompletedValue()
            }

            is NewServiceEvent.AddService -> {
                val fieldsIsEmtpy = checkFields()
                if (fieldsIsEmtpy) {
//                    TODO POPUP DIALOG
                } else {
                    addService()
                }
            }
            is NewServiceEvent.ChangeDurationValue -> {
                changeDurationValue(event.duration)
            }
            is NewServiceEvent.ChangePriceValue -> {
                changePriceValue(event.price)
            }
            is NewServiceEvent.ChangeNameValue -> {
                changeNameValue(event.name)
            }
        }
    }

    private fun changeCompletedValue() {
        state = state.copy(
            changeCompleted = false
        )
    }

    private fun addService() {
        val body = ServiceDto(state.duration.toInt(), state.name, state.price.toInt())
        job?.cancel()
        job = service.addService(body).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false, changeCompleted = true)
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

    private fun changePriceValue(price: String) {
        state = state.copy(price = price)
    }

    private fun changeNameValue(name: String) {
        state = state.copy(name = name)
    }

    private fun changeDurationValue(duration: String) {
        state = state.copy(duration = duration)
    }

    private fun checkFields(): Boolean {
        if (state.price.isEmpty() || state.duration.isEmpty()
            || state.name.isEmpty()
        ) {
            return true
        }
        return false
    }
}