package com.example.wavewash.presentation.orders.new_order

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.AddOrderDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.domain.use_cases.OrderUseCase
import com.example.wavewash.domain.use_cases.ServiceUseCase
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.presentation.janitors.update_janitor.UpdateJanitorEvents
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "NewOrderViewModel"

@HiltViewModel
class NewOrderViewModel @Inject
constructor(
    private val orderUseCase: OrderUseCase,
    private val serviceUseCase: ServiceUseCase,
    private val washerUseCase: WasherUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(NewOrderState())

    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null
    private var jobService: Job? = null
    private var jobWashers: Job? = null

    init {
        val washerId = savedStateHandle.get<Long>("washerId")
        if (washerId != null) {
            onTriggerEvent(NewOrderEvent.WasherOrderOrNot(washerId))
        }
        else{
            onTriggerEvent(NewOrderEvent.GetWashers)
        }
        onTriggerEvent(NewOrderEvent.GetServices)
    }

    fun onTriggerEvent(event: NewOrderEvent) {
        when (event) {
            is NewOrderEvent.WasherOrderOrNot -> {
                changeWasherOrderOrNot(event.washerId)
            }
            is NewOrderEvent.AddOrder -> {
                val fieldsIsEmpty = checkFields()
                if (fieldsIsEmpty) {
                    requiredFieldsPopup()
                } else {
                    addOrder()
                }
            }
            is NewOrderEvent.ChangeCarModel -> {
                changeCarModel(event.carModel)
            }
            is NewOrderEvent.ChangeCarNumber -> {
                changeCarNumber(event.carNumber)
            }
            is NewOrderEvent.ChangeClientName -> {
                changeClientName(event.clientName)
            }
            is NewOrderEvent.ChangeClientNumber -> {
                changeClientNumber(event.clientNumber)
            }

            is NewOrderEvent.GetServices -> {
                getServices()
            }
//            is NewOrderEvent.ReloadServices -> {
//                state = state.copy(
//                    servicePage = 0,
//                    serviceEndIsReached = false,
//                    servicesOfDialog = listOf()
//                )
//                getServices()
//            }
            is NewOrderEvent.ChangeService -> {
                updateService(event.service)
            }
            is NewOrderEvent.DeleteService -> {
                deleteService(event.service)
            }
            is NewOrderEvent.LoadMoreServices -> {
                state = state.copy(servicePage = state.servicePage + 1)
                getServices()
            }
            is NewOrderEvent.OnSearchQueryService -> {
                state = state.copy(
                    serviceSearchQuery = event.query,
                    servicePage = 0,
                    serviceEndIsReached = false,
                    servicesOfDialog = listOf()
                )
                getServices()
            }


            is NewOrderEvent.GetWashers -> {
                getWashers()
            }
//            is NewOrderEvent.ReloadWashers -> {
//                state = state.copy(
//                    washerPage = 0,
//                    washerEndIsReached = false,
//                    washersOfDialog = listOf()
//                )
//                getWashers()
//            }
            is NewOrderEvent.ChangeWasher -> {
                updateWasher(event.washer)
            }
            is NewOrderEvent.DeleteWasher -> {
                deleteWasher(event.washer)
            }
            is NewOrderEvent.LoadMoreWashers -> {
                state = state.copy(washerPage = state.washerPage + 1)
                getWashers()
            }
            is NewOrderEvent.OnSearchQueryWasher -> {
                state = state.copy(
                    washerSearchQuery = event.query,
                    washerPage = 0,
                    washerEndIsReached = false,
                    washersOfDialog = listOf()
                )
                getWashers()
            }

        }
    }

    private fun getServices() {
        jobService?.cancel()
        jobService = serviceUseCase.get_services(state.serviceSearchQuery, state.servicePage)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (state.servicesOfDialog == result.data) {
                            state = state.copy(serviceEndIsReached = true)
                        } else {
                            state = state.copy(servicesOfDialog = result.data!!)
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
        jobWashers?.cancel()
        jobWashers =
            washerUseCase.get_washers(state.washerSearchQuery, state.washerPage).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val freeWashers = result.data!!.filter {
                            !it.active
                        }
                        if (state.washersOfDialog == freeWashers) {
                            state = state.copy(washerEndIsReached = true)
                        } else {
                            state = state.copy(washersOfDialog = freeWashers)
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

    private fun addOrder() {
        val serviceIds = state.services.map {
            it.id
        }
        val washerIds = state.washers.map {
            it.id
        }
        val addOrderDto = AddOrderDto(
            clientNumber = state.clientNumber.toInt(),
            clientName = state.clientName,
            carModel = state.carModel,
            carNumber = state.carNumber,
            serviceIds = serviceIds,
            washerIds = washerIds
        )
        job?.cancel()
        job = orderUseCase.addOrder(addOrderDto).onEach { result ->
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

    private fun checkFields(): Boolean {
        if (state.carModel.isEmpty() || state.carNumber.isEmpty()
            || state.clientName.isEmpty() || state.clientNumber.isEmpty()
            || state.price == 0 || state.priceOfJanitorsStake == 0
        ) {
            return true
        }
        return false
    }

    private fun requiredFieldsPopup() {
        state = state.copy(
            requiredFields = true
        )
    }

    private fun changeWasherOrderOrNot(washerId: Long) {
//        jobWashers?.cancel()
        washerUseCase.get_washer(washerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state =
                        state.copy(washerId = washerId, washers = state.washers.plus(result.data!!))
                    Log.d(TAG, "changeWasherOrderOrNot: washerId $washerId state = ${state.washerId} ${state.washers}")
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
}