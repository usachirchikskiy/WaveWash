package com.example.wavewash.presentation.orders.change_order_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.OrderAddDto
import com.example.wavewash.data.remote.dto.order.OrderUpdateDto
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.domain.use_cases.Order
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.presentation.orders.new_order.NewOrderEvent
import com.example.wavewash.presentation.orders.new_order.NewOrderState
import com.example.wavewash.presentation.orders.order_details_screen.OrderDetailEvent
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.durationOfServices
import com.example.wavewash.utils.priceOfJanitorsStake
import com.example.wavewash.utils.priceOfServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "ChangeOrderViewModel"

@HiltViewModel
class ChangeOrderViewModel @Inject
constructor(
    private val order: Order,
    private val service: Service,
    private val washer: Washer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ChangeOrderState())

    private var job: Job? = null
    private var jobService: Job? = null
    private var jobWashers: Job? = null

    init {
        savedStateHandle.get<Long>("orderId")?.let { orderId ->
            state = state.copy(id = orderId)
            onTriggerEvent(ChangeOrderEvent.GetOrder)
            onTriggerEvent(ChangeOrderEvent.GetServices)
            onTriggerEvent(ChangeOrderEvent.GetWashers)
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
            is ChangeOrderEvent.ReloadServices -> {
                state = state.copy(servicePage = 0, serviceEndIsReached = false, servicesOfDialog = listOf())
                getServices()
            }
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
                state = state.copy(serviceSearchQuery = event.query, servicePage = 0, serviceEndIsReached = false, servicesOfDialog = listOf())
                getServices()
            }


            is ChangeOrderEvent.GetWashers -> {
                getWashers()
            }
            is ChangeOrderEvent.ReloadWashers -> {
                state = state.copy(washerPage = 0, washerEndIsReached = false, washersOfDialog = listOf())
                getWashers()
            }
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
            is ChangeOrderEvent.OnSearchQueryWasher-> {
                state = state.copy(washerSearchQuery = event.query, washerPage = 0, washerEndIsReached = false, washersOfDialog = listOf())
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

                val fieldsIsEmpty = checkFields()
                if (fieldsIsEmpty) {
                    requiredFieldsPopup()
                } else {
                    saveChanges()
                }
            }
            is ChangeOrderEvent.CancelOrder -> {
                cancelOrder(event.cancelledReason)
            }
            is ChangeOrderEvent.Back -> {
                changeCompletedValue()
            }
        }
    }

    private fun getOrder(orderId: Long) {
        job?.cancel()
        job = order.get_order(orderId).onEach { result ->
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
        jobService?.cancel()
        jobService = service.get_services(state.serviceSearchQuery, state.servicePage).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data!!.isEmpty()) {
                        state = state.copy(serviceEndIsReached = true)
                    }
                    val listAll = state.servicesOfDialog.plus(result.data)
                    state = state.copy(servicesOfDialog = listAll, serviceIsLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, serviceIsLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(serviceIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWashers(){
        jobWashers?.cancel()
        jobWashers = washer.get_washers(state.washerSearchQuery, state.washerPage).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if(result.data!!.isEmpty()){
                        state = state.copy(washerEndIsReached = true)
                    }
                    val listAll = state.washersOfDialog.plus(result.data)
                    state = state.copy(washersOfDialog = listAll, washerIsLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, washerIsLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(washerIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveChanges() {
        val orderUpdate = OrderUpdateDto(
            cancelled = false,
            cancelledReason = "",
            active = true,
            clientNumber = state.clientNumber.toInt(),
            clientName = state.clientName,
            carModel = state.carModel,
            carNumber = state.carNumber,
            serviceIds = state.services.map { it.id },
            washerIds = state.washers.map { it.id }
        )
        job?.cancel()
        job = order.update(orderUpdate, state.id).onEach { result ->
            state = when (result) {
                is Resource.Success -> {
                    state.copy(changeCompleted = true, orderIsLoading = false)
                }
                is Resource.Error -> {
                    state.copy(error = result.message!!, orderIsLoading = false)
                }
                is Resource.Loading -> {
                    state.copy(orderIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun cancelOrder(cancelledReason: String) {
        val orderUpdate = OrderUpdateDto(
            cancelled = true,
            cancelledReason = cancelledReason,
            active = false,
            clientNumber = state.clientNumber.toInt(),
            clientName = state.clientName,
            carModel = state.carModel,
            carNumber = state.carNumber,
            serviceIds = state.services.map { it.id },
            washerIds = state.washers.map { it.id }
        )
        job?.cancel()
        job = order.update(orderUpdate, state.id).onEach { result ->
            state = when (result) {
                is Resource.Success -> {
                    state.copy(changeCompleted = true, orderIsLoading = false)
                }
                is Resource.Error -> {
                    state.copy(error = result.message!!, orderIsLoading = false)
                }
                is Resource.Loading -> {
                    state.copy(orderIsLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateService(service: ServiceAnswerDto) {
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

    private fun updateWasher(washer: WasherAnswerDto) {
        val ids = state.washers.plus(washer)
        val priceOfJanitorsStake = priceOfJanitorsStake(ids, state.price)
        state = state.copy(
            washers = ids,
            priceOfJanitorsStake = priceOfJanitorsStake
        )
    }

    private fun deleteService(service: ServiceAnswerDto) {
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

    private fun deleteWasher(washer: WasherAnswerDto) {
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

    private fun changeCompletedValue() {
        state = state.copy(
            changeCompleted = false
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
}