package com.example.wavewash.presentation.orders.new_order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.OrderAddDto
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.domain.use_cases.Order
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.presentation.orders.change_order_screen.ChangeOrderEvent
import com.example.wavewash.presentation.orders.orders_screen.OrderState
import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent
import com.example.wavewash.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject
constructor(
    private val order: Order,
    private val service: Service,
    private val washer: Washer,
) : ViewModel() {

    var state by mutableStateOf(NewOrderState())

    private var job: Job? = null
    private var jobService: Job? = null
    private var jobWashers: Job? = null

    init {
        onTriggerEvent(NewOrderEvent.GetServices)
        onTriggerEvent(NewOrderEvent.GetWashers)
    }

    fun onTriggerEvent(event: NewOrderEvent) {
        when (event) {
            is NewOrderEvent.Back -> {
                changeCompletedValue()
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
            is NewOrderEvent.ReloadServices -> {
                state = state.copy(servicePage = 0, serviceEndIsReached = false, servicesOfDialog = listOf())
                getServices()
            }
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
                state = state.copy(serviceSearchQuery = event.query, servicePage = 0, serviceEndIsReached = false, servicesOfDialog = listOf())
                getServices()
            }


            is NewOrderEvent.GetWashers -> {
                getWashers()
            }
            is NewOrderEvent.ReloadWashers -> {
                state = state.copy(washerPage = 0, washerEndIsReached = false, washersOfDialog = listOf())
                getWashers()
            }
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
            is NewOrderEvent.OnSearchQueryWasher-> {
                state = state.copy(washerSearchQuery = event.query, washerPage = 0, washerEndIsReached = false, washersOfDialog = listOf())
                getWashers()
            }

        }
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

    private fun addOrder() {
        val serviceIds = state.services.map {
            it.id
        }
        val washerIds = state.washers.map {
            it.id
        }
        val addOrderDto = OrderAddDto(
            clientNumber = state.clientNumber.toInt(),
            clientName = state.clientName,
            carModel = state.carModel,
            carNumber = state.carNumber,
            serviceIds = serviceIds,
            washerIds = washerIds
        )
        job?.cancel()
        job = order.addOrder(addOrderDto).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(orderIsLoading = false, changeCompleted = true)
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

    private fun requiredFieldsPopup(){
        state = state.copy(
            requiredFields = true
        )
    }
}