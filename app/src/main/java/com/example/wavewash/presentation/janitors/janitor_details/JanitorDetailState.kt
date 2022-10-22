package com.example.wavewash.presentation.janitors.janitor_details

import com.example.wavewash.data.remote.dto.order.OrderAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto

data class JanitorDetailState(
    val id:Long = -1,
    val isActive:Boolean = true,
    val dateFrom:String = "",
    val dateTo:String = "",
    val page:Int = 0,

    val calendarDateFrom:String = "",
    val calendarDateTo:String = "",

    val orders:List<OrderAnswerDto> = listOf(),

    val washer:WasherAnswerDto? = null,

    val earnedMoney:String="",
    val earnedStake: String = "",

    val isLoading:Boolean = false,
    val endReached:Boolean = false,
    val error: String = ""
)