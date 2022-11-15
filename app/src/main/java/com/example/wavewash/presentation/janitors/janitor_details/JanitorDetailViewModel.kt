package com.example.wavewash.presentation.janitors.janitor_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JanitorDetailViewModel @Inject
constructor(
    private val washerUseCase: WasherUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(JanitorDetailState())
    private var job: Job? = null
    private var jobOrders: Job? = null

    init {
        savedStateHandle.get<Long>("washerId")?.let { washerId ->
            state = state.copy(id = washerId)
            onTriggerEvent(JanitorDetailEvent.GetJanitor)
        }
    }

    fun onTriggerEvent(event: JanitorDetailEvent) {
        when (event) {
            is JanitorDetailEvent.GetJanitor -> {
                todayDates()
                visibleTabs()
                getJanitor()
                getJanitorOrders()
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
                visibleTabs()
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.OnNextDateClick -> {
                onNextDate()
                visibleTabs()
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
            is JanitorDetailEvent.OnPreviousDateClick -> {
                onPreviousDate()
                visibleTabs()
                getJanitorOrders()
                getEarnedMoney()
                getEarnedStake()
            }
//            is JanitorDetailEvent.ReloadWasher->{
//                getJanitor()
//                getEarnedMoney()
//                getEarnedStake()
//            }
//            is JanitorDetailEvent.ReloadOrders->{
//                state = state.copy(page = 0)
//                getJanitorOrders()
//            }
        }
    }

    private fun todayDates() {
        val pair = getFromAndTo()
        val calendarDateFrom = getDate()
        state = state.copy(
            todayDate = pair.first,
            calendarDateFrom = calendarDateFrom,
            dateFrom = pair.first,
            dateTo = pair.second
        )
    }

    private fun getJanitor() {
        job?.cancel()
        job = washerUseCase.get_washer(state.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(washer = result.data)
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

    private fun getJanitorOrders() {
        jobOrders?.cancel()
        jobOrders = washerUseCase.get_washer_orders(state.id,state.isActive,state.dateFrom,state.dateTo,state.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(orders = result.data!!)
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
        washerUseCase.get_washer_earnedMoney(state.id,state.dateFrom,state.dateTo).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(earnedMoney = result.data.toString())
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

    private fun getEarnedStake(){
        washerUseCase.get_washer_earnedStake(state.id,state.dateFrom,state.dateTo).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(earnedStake = result.data.toString())
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

    private fun visibleTabs(){
        state = if(state.todayDate==state.dateFrom && !state.calendarDateFrom.contains("-")){
            state.copy(isVisibleTabs = true)
        } else{
            state.copy(isVisibleTabs = false, isActive = false)
        }
    }
}