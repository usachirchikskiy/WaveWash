package com.example.wavewash.presentation.orders.orders_screen

import com.example.wavewash.domain.model.Order

data class OrderState(
    val todayDate:String = "",
    val calendarDateFrom:String = "",
    val calendarDateTo:String = "",

    val isVisibleTabs:Boolean = false,

    val orders:List<Order> = listOf(),
    val page:Int = 0,
    val isActive:Boolean = true,
    val dateFrom:String = "",
    val dateTo:String = "",

    val isLoading:Boolean = false,
    val error:String = "",
    val endReached:Boolean = false,

    )
