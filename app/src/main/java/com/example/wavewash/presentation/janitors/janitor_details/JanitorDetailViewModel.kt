package com.example.wavewash.presentation.janitors.janitor_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JanitorDetailViewModel @Inject
constructor(
    private val washer: Washer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(JanitorDetailState())
    private var job: Job? = null

    init {
        val pair = getFromAndTo()
        val calendarDateFrom = getDate()
        state = state.copy(
            calendarDateFrom = calendarDateFrom,
            dateFrom = pair.first,
            dateTo = pair.second
        )
        savedStateHandle.get<Long>("washerId")?.let { washerId ->
            state = state.copy(id = washerId)
            onTriggerEvent(JanitorDetailEvent.GetJanitor)
        }
    }

    fun onTriggerEvent(event: JanitorDetailEvent) {
        when (event) {
            is JanitorDetailEvent.GetJanitor -> {
                getJanitor()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.ActiveOrders -> {
                state = state.copy(
                    orders = listOf(),
                    isActive = true,
                    page = 0,
                    endReached = false
                )
                getJanitorOrders()
            }
            is JanitorDetailEvent.FinishedOrders -> {
                state = state.copy(
                    orders = listOf(),
                    isActive = false,
                    page = 0,
                    endReached = false
                )
                getJanitorOrders()
            }
            is JanitorDetailEvent.ChangeDates -> {
                changeDates(event.dateFrom, event.dateTo)
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.OnNextDateClick -> {
                onNextDate()
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.OnPreviousDateClick -> {
                onPreviousDate()
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.ReloadOrders->{
                state = state.copy(page = 0, endReached = false, orders = listOf())
                getJanitorOrders()
            }
        }
    }

    private fun getJanitor() {
        job?.cancel()
        job = washer.get_washer(state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(washer = result.data, isLoading = false)
                    getJanitorOrders()
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

    private fun getJanitorOrders() {
        job?.cancel()
        job = washer.get_washer_orders(state.id,state.isActive,state.dateFrom,state.dateTo,state.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(orders = result.data!!, isLoading = false)
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

    private fun getEarnedMoney(){
        washer.get_washer_earnedMoney(state.id,state.dateFrom,state.dateTo).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(earnedMoney = result.data.toString(), isLoading = false)
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

    private fun getEarnedStake(){
        washer.get_washer_earnedStake(state.id,state.dateFrom,state.dateTo).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(earnedStake = result.data.toString(), isLoading = false)
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