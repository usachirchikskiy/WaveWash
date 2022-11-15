package com.example.wavewash.presentation.janitors.janitor_details

import com.example.wavewash.data.remote.dto.order.OrderDto
import com.example.wavewash.domain.model.Order
import com.example.wavewash.domain.model.Washer

data class JanitorDetailState(
    val id:Long = -1,
    val isActive:Boolean = true,
    val dateFrom:String = "",
    val dateTo:String = "",
    val page:Int = 0,

    val isVisibleTabs:Boolean = false,

    val todayDate:String = "",
    val calendarDateFrom:String = "",
    val calendarDateTo:String = "",

    val orders:List<Order> = listOf(),

    val washer: Washer? = null,

    val earnedMoney:String="",
    val earnedStake: String = "",

    val isLoading:Boolean = false,
    val endReached:Boolean = false,
    val error: String = ""
)