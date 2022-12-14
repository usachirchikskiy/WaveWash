package com.example.wavewash.presentation.services.new_service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.service.AddServiceDto
import com.example.wavewash.domain.use_cases.ServiceUseCase
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceName
import com.example.wavewash.domain.validation_use_case.service.ValidationServicePrice
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceTime
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewServiceViewModel @Inject
constructor(
    private val serviceUseCase: ServiceUseCase,
    private val validationServiceName: ValidationServiceName,
    private val validationServicePrice: ValidationServicePrice,
    private val validationServiceTime: ValidationServiceTime
) : ViewModel() {

    var state by mutableStateOf(NewServiceState())
    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null

    fun onTriggerEvent(event: NewServiceEvent) {
        when (event) {
            is NewServiceEvent.AddService -> {
                addService()
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


    private fun addService() {
        val nameResult = validationServiceName.execute(state.name)
        val priceResult = validationServicePrice.execute(state.price)
        val durationResult = validationServiceTime.execute(state.duration)

        val hasError = listOf(
            nameResult,
            priceResult,
            durationResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                priceError = priceResult.errorMessage,
                durationError = durationResult.errorMessage,
            )
            return
        }

        val body = AddServiceDto(state.duration.toInt(), state.name, state.price.toInt())
        job?.cancel()
        job = serviceUseCase.addService(body).onEach { result ->
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

    private fun changePriceValue(price: String) {
        state = state.copy(price = price)
    }

    private fun changeNameValue(name: String) {
        state = state.copy(name = name)
    }

    private fun changeDurationValue(duration: String) {
        state = state.copy(duration = duration)
    }


//                val fieldsIsEmtpy = checkFields()
//                if (fieldsIsEmtpy) {
//                    TODO POPUP DIALOG
//                } else {
//                    addService()
//                }
//    private fun checkFields(): Boolean {
//        if (state.price.isEmpty() || state.duration.isEmpty() || state.name.isEmpty()) return true
//        return false
//    }
}