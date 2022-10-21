package com.example.wavewash.presentation.orders.order_details_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.order.OrderUpdateDto
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.domain.use_cases.Order
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.presentation.services.new_service.NewServiceEvent
import com.example.wavewash.presentation.services.new_service.NewServiceState
import com.example.wavewash.presentation.services.update_service.UpdateServiceEvent
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.durationOfServices
import com.example.wavewash.utils.priceOfJanitorsStake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "OrderDetailViewModel"
@HiltViewModel
class OrderDetailViewModel @Inject
constructor(
    private val order: Order,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrderDetailState())
    private var job: Job? = null

    init {
        Log.d(TAG, "INIT ")
        savedStateHandle.get<Long>("orderId")?.let { orderId ->
            state = state.copy(id = orderId)
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
        val orderUpdate = OrderUpdateDto(
            active = false,
            cancelled = false,
            cancelledReason = "",
            clientNumber = state.clientNumber,
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
                    state.copy(completed = true,isLoading = false)
                }
                is Resource.Error -> {
                    state.copy(error = result.message!!, isLoading = false)
                }
                is Resource.Loading -> {
                    state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrder(orderId: Long) {
        job?.cancel()
        job = order.get_order(orderId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val order = result.data
                    order?.let { it ->
                        state = state.copy(
                            isActive = it.active,
                            isLoading = false,
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
                    state = state.copy(error = result.message!!, isLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeCompleted(){
        state = state.copy(completed = false)
    }
}