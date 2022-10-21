package com.example.wavewash.presentation.orders.orders_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.Order
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorState
import com.example.wavewash.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "OrdersViewModel"

@HiltViewModel
class OrdersViewModel @Inject
constructor(
    private val order: Order
) : ViewModel() {

    var state by mutableStateOf(OrderState())

    private var job: Job? = null

    init {
        val pair = getFromAndTo()
        val calendarDateFrom = getDate()
        state = state.copy(
            calendarDateFrom = calendarDateFrom,
            dateFrom = pair.first,
            dateTo = pair.second
        )
        getOrders()
    }

    fun onTriggerEvent(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.GetOrders -> {
                state = state.copy(page = state.page + 1)
                getOrders()
            }
            is OrdersEvent.ActiveOrders -> {
                state = state.copy(
                    orders = listOf(),
                    isActive = true,
                    page = 0,
                    endReached = false
                )
                getOrders()
            }
            is OrdersEvent.FinishedOrders -> {
                state = state.copy(
                    orders = listOf(),
                    isActive = false,
                    page = 0,
                    endReached = false
                )
                getOrders()
            }
            is OrdersEvent.ChangeDates -> {
                changeDates(event.dateFrom, event.dateTo)
                getOrders()
            }
            is OrdersEvent.OnNextDateClick -> {
                onNextDate()
                getOrders()
            }
            is OrdersEvent.OnPreviousDateClick -> {
                onPreviousDate()
                getOrders()
            }
            is OrdersEvent.ReloadOrders->{
                state = state.copy(page = 0, endReached = false, orders = listOf())
                getOrders()
            }
        }
    }

    private fun onPreviousDate() {
        val previousDateFrom = getPreviousDate(state.dateFrom)
        val previousCalendarDateFrom = getDateOnRuLang(previousDateFrom)
        if(state.calendarDateTo != ""){
            state = state.copy(
                calendarDateFrom = previousCalendarDateFrom,
                dateFrom = previousDateFrom,
                page = 0,
                orders = listOf(),
                endReached = false
            )
        }
        else{
            val previousDateTo = getPreviousDate(state.dateTo)
            state = state.copy(
                calendarDateFrom = previousCalendarDateFrom,
                calendarDateTo = "",
                dateFrom = previousDateFrom,
                dateTo = previousDateTo,
                page = 0,
                orders = listOf(),
                endReached = false
            )
        }

    }

    private fun onNextDate() {
        val dateFrom = state.dateFrom
        val nextDateFrom = getNextDate(dateFrom)
        val calendarDateFrom = getDateOnRuLang(nextDateFrom)

        if (state.calendarDateTo != "") {
            val dateTo = state.dateTo
            val days = getDifferenceBetweenDates(dateFrom, dateTo)
            if (days > 1) {
                state = state.copy(
                    calendarDateFrom = calendarDateFrom,
                    dateFrom = nextDateFrom,
                    page = 0,
                    orders = listOf(),
                    endReached = false
                )
            } else {
                state = state.copy(
                    calendarDateFrom = calendarDateFrom,
                    calendarDateTo = "",
                    dateFrom = nextDateFrom,
                    page = 0,
                    orders = listOf(),
                    endReached = false
                )
            }
        } else {
            val nextDateTo = getNextDate(nextDateFrom)
            state = state.copy(
                calendarDateFrom = calendarDateFrom,
                calendarDateTo = "",
                dateFrom = nextDateFrom,
                dateTo = nextDateTo,
                page = 0,
                orders = listOf(),
                endReached = false
            )
        }
    }

    private fun changeDates(dateTo: String, dateFrom: String) {
        if (dateTo < dateFrom) {
            val calendarDateFrom = getDateOnRuLang(dateTo)
            val calendarDateTo = getDateOnRuLang(dateFrom)
            state = state.copy(
                orders = listOf(),
                calendarDateTo = calendarDateTo,
                calendarDateFrom = calendarDateFrom,
                dateFrom = dateTo,
                dateTo = dateFrom,
                page = 0,
                endReached = false
            )
        } else if (dateTo > dateFrom) {
            val calendarDateFrom = getDateOnRuLang(dateFrom)
            val calendarDateTo = getDateOnRuLang(dateTo)
            state = state.copy(
                orders = listOf(),
                calendarDateTo = calendarDateTo,
                calendarDateFrom = calendarDateFrom,
                dateFrom = dateFrom,
                dateTo = dateTo,
                page = 0,
                endReached = false
            )
        } else {
            val calendarDateFrom = getDateOnRuLang(dateFrom)
            val dateTo = getNextDate(dateFrom)
            state = state.copy(
                orders = listOf(),
                calendarDateTo = "",
                calendarDateFrom = calendarDateFrom,
                dateFrom = dateFrom,
                dateTo = dateTo,
                page = 0,
                endReached = false
            )
        }
    }

    private fun getOrders() {
        job?.cancel()
        job = order.get_orders(state.isActive, state.dateFrom, state.dateTo, state.page)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data!!.isEmpty()) {
                            state = state.copy(endReached = true)
                        }
                        val listAll = state.orders.plus(result.data)
                        state = state.copy(orders = listAll, isLoading = false)
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
}