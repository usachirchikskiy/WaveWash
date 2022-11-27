package com.example.wavewash.presentation.orders.orders_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.OrderUseCase
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
    private val orderUseCase: OrderUseCase
) : ViewModel() {

    var state by mutableStateOf(OrderState())

    private var job: Job? = null

    init {
        onTriggerEvent(OrdersEvent.GetFirstPage)
    }
    
    fun onTriggerEvent(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.GetFirstPage -> {
                todayDates()
                visibleTabs()
                getOrders()
            }
            is OrdersEvent.GetOrders -> {
                state = state.copy(page = state.page + 1)
                getOrders()
            }
            is OrdersEvent.ActiveOrders -> {
                if(state.isActive) return
                state = state.copy(
                    orders = listOf(),
                    isActive = true,
                    page = 0,
                    endReached = false
                )
                getOrders()
            }
            is OrdersEvent.FinishedOrders -> {
                if(!state.isActive) return
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
                visibleTabs()
                getOrders()
            }
            is OrdersEvent.OnNextDateClick -> {
                onNextDate()
                visibleTabs()
                getOrders()
            }
            is OrdersEvent.OnPreviousDateClick -> {
                onPreviousDate()
                visibleTabs()
                getOrders()
            }
            is OrdersEvent.ReloadOrders->{
                state = state.copy(page = 0, endReached = false, orders = listOf())
                getOrders()
            }
        }
    }

    private fun todayDates(){
        val pair = getFromAndTo()
        val calendarDateFrom = getDate()
        Log.d(TAG, "todayDates: ${pair.first} ${pair.second} $calendarDateFrom")
        state = state.copy(
            todayDate = pair.first,
            calendarDateFrom = calendarDateFrom,
            dateFrom = pair.first,
            dateTo = pair.second
        )
    }

    private fun getOrders() {
        job?.cancel()
        job = orderUseCase.get_orders(state.isActive, state.dateFrom, state.dateTo, state.page)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state = if (state.orders == result.data) {
                            state.copy(endReached = true)
                        } else {
                            state.copy(orders = result.data!!)
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

    private fun changeDates(dateFrom: String, dateTo: String) {
        val calendarDateFrom = getDateOnRuLang(dateFrom)
        val stateDateTo = getNextDate(dateTo)
        if (dateFrom < dateTo) {
            val calendarDateTo = getDateOnRuLang(dateTo)
            state = state.copy(
                orders = listOf(),
                calendarDateTo = calendarDateTo,
                calendarDateFrom = calendarDateFrom,
                dateFrom = dateFrom,
                dateTo = stateDateTo,
                page = 0,
                endReached = false
            )
        } else {
            state = state.copy(
                orders = listOf(),
                calendarDateTo = "",
                calendarDateFrom = calendarDateFrom,
                dateFrom = dateFrom,
                dateTo = stateDateTo,
                page = 0,
                endReached = false
            )
        }
    }

    private fun onNextDate():Boolean {
        val dateFrom = state.dateFrom
        val todayDate = state.todayDate
        val nextDateFrom = getNextDate(dateFrom)
        val calendarDateFrom = getDateOnRuLang(nextDateFrom)
        if (dateFrom < todayDate) {
            if (state.calendarDateTo != "") {
                val dateTo = state.dateTo
                val days = getDifferenceBetweenDates(dateFrom, dateTo)
                if (days > 2) {
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
            return true
        }
        return false
    }

    private fun onPreviousDate() {
        val dateFrom = state.dateFrom
        val previousDateFrom = getPreviousDate(dateFrom)
        val previousCalendarDateFrom = getDateOnRuLang(previousDateFrom)
        if (state.calendarDateTo != "") {
            state = state.copy(
                calendarDateFrom = previousCalendarDateFrom,
                dateFrom = previousDateFrom,
                page = 0,
                orders = listOf(),
                endReached = false
            )
        } else {
            val dateTo = state.dateTo
            val previousDateTo = getPreviousDate(dateTo)
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

    private fun visibleTabs(){
        state = if (state.todayDate == state.dateFrom) {
            state.copy(isVisibleTabs = true, isActive = true)
        } else {
            state.copy(isVisibleTabs = false, isActive = false)
        }
    }

}