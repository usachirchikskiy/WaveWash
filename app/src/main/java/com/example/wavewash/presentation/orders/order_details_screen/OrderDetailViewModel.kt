package com.example.wavewash.presentation.orders.order_details_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.UpdateOrderDto
import com.example.wavewash.domain.use_cases.OrderUseCase
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.durationOfServices
import com.example.wavewash.utils.priceOfJanitorsStake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "OrderDetailViewModel"
@HiltViewModel
class OrderDetailViewModel @Inject
constructor(
    private val orderUseCase: OrderUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrderDetailState())
    private val _eventFlow = MutableSharedFlow<NavigationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null

    init {
        savedStateHandle.get<Long>("orderId")?.let { orderId ->
            onTriggerEvent(OrderDetailEvent.GetOrder(orderId))
        }
    }

    fun onTriggerEvent(event: OrderDetailEvent) {
        when (event) {
            is OrderDetailEvent.GetOrder -> {
                getOrder(event.orderId)
            }
            is OrderDetailEvent.CompleteOrder -> {
                completeOrder()
            }
        }
    }

    private fun completeOrder() {
        val orderUpdate = UpdateOrderDto(
            active = false,
            clientNumber = state.clientNumber,
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
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrder(orderId: Long) {
        job?.cancel()
        job = orderUseCase.get_order(orderId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val order = result.data
                    order?.let { it ->
                        state = state.copy(
                            id = orderId,
                            isActive = it.active,
                            carModel = it.carModel,
                            carNumber = it.carNumber,
                            clientName = it.clientName,
                            clientNumber = it.clientNumber,
                            services = it.services,
                            washers = it.washers,
                            price = it.price,
                            priceOfJanitorsStake = priceOfJanitorsStake(it.washers, it.price),
                            duration = durationOfServices(it.services).toString()
                        )
                    }

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

}