package com.example.wavewash.presentation.orders.change_order_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.UpdateOrderDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.domain.use_cases.OrderUseCase
import com.example.wavewash.domain.use_cases.ServiceUseCase
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.domain.validation_use_case.order.*
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.durationOfServices
import com.example.wavewash.utils.priceOfJanitorsStake
import com.example.wavewash.utils.priceOfServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "ChangeOrderViewModel"

@HiltViewModel
class ChangeOrderViewModel @Inject
constructor(
    private val orderUseCase: OrderUseCase,
    private val serviceUseCase: ServiceUseCase,
    private val washerUseCase: WasherUseCase,
    private val validationOrderServices: ValidationOrderServices,
    private val validationOrderWashers: ValidationOrderWashers,
    private val validationOrderCarNumber: ValidationOrderCarNumber,
    private val validationClientName: ValidationClientName,
    private val validationClientTelephoneNumber: ValidationClientTelephoneNumber,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ChangeOrderState())
    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null
    private var jobService: Job? = null
    private var jobWashers: Job? = null

    init {
        savedStateHandle.get<Long>("orderId")?.let { orderId ->
            state = state.copy(id = orderId)
            onTriggerEvent(ChangeOrderEvent.GetOrder)
        }
    }

    fun onTriggerEvent(event: ChangeOrderEvent) {
        when (event) {
            is ChangeOrderEvent.GetOrder -> {
                getOrder(state.id)
            }
            is ChangeOrderEvent.GetServices -> {
                getServices()
            }
//            is ChangeOrderEvent.ReloadServices -> {
//                state = state.copy(servicePage = 0, serviceEndIsReached = false, servicesOfDialog = listOf())
////                getServices()
//            }
            is ChangeOrderEvent.ChangeService -> {
                updateService(event.service)
            }
            is ChangeOrderEvent.DeleteService -> {
                deleteService(event.service)
            }
            is ChangeOrderEvent.LoadMoreServices -> {
                state = state.copy(servicePage = state.servicePage + 1)
                getServices()
            }
            is ChangeOrderEvent.OnSearchQueryService -> {
                state = state.copy(
                    serviceSearchQuery = event.query,
                    servicePage = 0,
                    serviceEndIsReached = false,
                    servicesOfDialog = listOf()
                )
                getServices()
            }
            is ChangeOrderEvent.GetWashers -> {
                getWashers()
            }
//            is ChangeOrderEvent.ReloadWashers -> {
//                state = state.copy(washerPage = 0, washerEndIsReached = false, washersOfDialog = listOf())
//                getWashers()
//            }
            is ChangeOrderEvent.ChangeWasher -> {
                updateWasher(event.washer)
            }
            is ChangeOrderEvent.DeleteWasher -> {
                deleteWasher(event.washer)
            }
            is ChangeOrderEvent.LoadMoreWashers -> {
                state = state.copy(washerPage = state.washerPage + 1)
                getWashers()
            }
            is ChangeOrderEvent.OnSearchQueryWasher -> {
                state = state.copy(
                    washerSearchQuery = event.query,
                    washerPage = 0,
                    washerEndIsReached = false,
                    washersOfDialog = listOf()
                )
                getWashers()
            }
            is ChangeOrderEvent.ChangeCarModel -> {
                changeCarModel(event.carModel)
            }
            is ChangeOrderEvent.ChangeCarNumber -> {
                changeCarNumber(event.carNumber)
            }
            is ChangeOrderEvent.ChangeClientName -> {
                changeClientName(event.clientName)
            }
            is ChangeOrderEvent.ChangeClientNumber -> {
                changeClientNumber(event.clientNumber)
            }
            is ChangeOrderEvent.SaveChanges -> {
                saveChanges()
            }
            is ChangeOrderEvent.CancelOrder -> {
                cancelOrder(event.cancelledReason)
            }
        }
    }

    private fun getOrder(orderId: Long) {
        job?.cancel()
        job = orderUseCase.get_order(orderId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val order = result.data
                    order?.let { it ->
                        state = state.copy(
                            orderIsLoading = false,
                            carModel = it.carModel,
                            carNumber = it.carNumber,
                            clientName = it.clientName,
                            clientNumber = it.clientNumber.toString(),
                            services = it.services,
                            washers = it.washers,
                            price = it.price,
                            priceOfJanitorsStake = priceOfJanitorsStake(it.washers, it.price),
                            duration = durationOfServices(it.services).toString()
                        )
                    }

                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, orderIsLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(orderIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getServices() {
        val ids = state.services.map {
            it.id
        }
        jobService?.cancel()
        jobService = serviceUseCase.get_not_checked_services(ids,state.serviceSearchQuery, state.servicePage)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state = if (state.servicesOfDialog == result.data) {
                            state.copy(serviceEndIsReached = true)
                        } else {
                            state.copy(servicesOfDialog = result.data!!)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(error = result.message!!)
                    }
                    is Resource.Loading -> {
                        state = state.copy(serviceIsLoading = result.isLoading)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getWashers() {
        val ids = state.washers.map {
            it.id
        }
        jobWashers?.cancel()
        jobWashers =
            washerUseCase.get_not_checked_washers(ids, state.washerSearchQuery, state.washerPage)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (state.washersOfDialog == result.data) {
                                state = state.copy(washerEndIsReached = true)
                            } else {
                                state = state.copy(washersOfDialog = result.data!!)
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(error = result.message!!)
                        }
                        is Resource.Loading -> {
                            state = state.copy(washerIsLoading = result.isLoading)
                        }
                    }
                }.launchIn(viewModelScope)
    }

    private fun saveChanges() {
        val servicesResult = validationOrderServices.execute(state.services)
        val washersResult = validationOrderWashers.execute(state.washers)
        val carNumberResult = validationOrderCarNumber.execute(state.carNumber)
        val clientNameResult = validationClientName.execute(state.clientName)
        val clientTelephoneNumberResult =
            validationClientTelephoneNumber.execute(state.clientNumber)

        val hasError = listOf(
            servicesResult,
            washersResult,
            carNumberResult,
            clientNameResult,
            clientTelephoneNumberResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                servicesError = servicesResult.errorMessage,
                washersError = washersResult.errorMessage,
                carNumberError = carNumberResult.errorMessage,
                clientNameError = clientNameResult.errorMessage,
                clientTelephoneNumberError = clientTelephoneNumberResult.errorMessage
            )
            return
        }

        val orderUpdate = UpdateOrderDto(
            active = true,
            clientNumber = state.clientNumber.toInt(),
            clientName = state.clientName,
            carModel = state.carModel,
            carNumber = state.carNumber,
            serviceIds = state.services.map { it.id },
            washerIds = state.washers.map { it.id }
        )
        job?.cancel()
        job = orderUseCase.update(orderUpdate, state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(NavigationEvent.GoBack)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!)
                }
                is Resource.Loading -> {
                    state = state.copy(orderIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun cancelOrder(cancelledReason: String) {
        job?.cancel()
        job = orderUseCase.delete_order(orderId = state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(NavigationEvent.GoBack)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, orderIsLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(orderIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateService(service: Service) {
        val ids = state.services.plus(service)
        val duration = durationOfServices(ids)
        val price = priceOfServices(ids)

        if (state.washers.isNotEmpty()) {
            val priceOfJanitorsStake = priceOfJanitorsStake(state.washers, price)
            state = state.copy(
                duration = duration.toString(),
                services = ids,
                price = price,
                priceOfJanitorsStake = priceOfJanitorsStake
            )
        } else {
            state = state.copy(
                duration = duration.toString(),
                services = ids,
                price = price
            )
        }
    }

    private fun updateWasher(washer: Washer) {
        val ids = state.washers.plus(washer)
        val priceOfJanitorsStake = priceOfJanitorsStake(ids, state.price)
        state = state.copy(
            washers = ids,
            priceOfJanitorsStake = priceOfJanitorsStake
        )
    }

    private fun deleteService(service: Service) {
        val ids = state.services.minus(service)
        val duration = durationOfServices(ids)
        val price = priceOfServices(ids)
        if (state.washers.isNotEmpty()) {
            val priceOfJanitorsStake = priceOfJanitorsStake(state.washers, price)
            state = state.copy(
                duration = duration.toString(),
                services = ids,
                price = price,
                priceOfJanitorsStake = priceOfJanitorsStake
            )
        } else {
            state = state.copy(
                duration = duration.toString(),
                services = ids,
                price = price
            )
        }
    }

    private fun deleteWasher(washer: Washer) {
        val ids = state.washers.minus(washer)
        val priceOfJanitorsStake = priceOfJanitorsStake(ids, state.price)
        state = state.copy(
            washers = ids,
            priceOfJanitorsStake = priceOfJanitorsStake
        )
    }

    private fun changeClientNumber(clientNumber: String) {
        state = state.copy(
            clientNumber = clientNumber
        )
    }

    private fun changeClientName(clientName: String) {
        state = state.copy(
            clientName = clientName
        )
    }

    private fun changeCarNumber(carNumber: String) {
        state = state.copy(
            carNumber = carNumber
        )
    }

    private fun changeCarModel(carModel: String) {
        state = state.copy(
            carModel = carModel
        )
    }

//    private fun changeCompletedValue() {
//        state = state.copy(
//            changeCompleted = false
//        )
//    }

//    private fun checkFields(): Boolean {
//        if (state.carModel.isEmpty() || state.carNumber.isEmpty()
//            || state.clientName.isEmpty() || state.clientNumber.isEmpty()
//            || state.price == 0 || state.priceOfJanitorsStake == 0
//        ) {
//            return true
//        }
//        return false
//    }
//
//    private fun requiredFieldsPopup() {
//        state = state.copy(
//            requiredFields = true
//        )
//    }
}