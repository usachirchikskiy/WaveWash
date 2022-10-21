package com.example.wavewash.presentation.orders.orders_screen

import com.example.wavewash.data.remote.dto.order.OrderAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto

data class OrderState(
    val orders:List<OrderAnswerDto> = listOf(),
    val page:Int = 0,
    val isActive:Boolean = true,
    val dateFrom:String = "",
    val dateTo:String = "",
    val isLoading:Boolean = false,
    val error:String = "",
    val endReached:Boolean = false,
    val calendarDateFrom:String = "",
    val calendarDateTo:String = "",
)
