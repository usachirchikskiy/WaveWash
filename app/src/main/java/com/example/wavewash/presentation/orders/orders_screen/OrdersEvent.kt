package com.example.wavewash.presentation.orders.orders_screen

import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents

sealed class OrdersEvent {
    object GetFirstPage : OrdersEvent()

    object GetOrders : OrdersEvent()

    object ActiveOrders : OrdersEvent()

    object FinishedOrders : OrdersEvent()

    data class ChangeDates(val dateFrom: String, val dateTo: String) : OrdersEvent()

    object ReloadOrders : OrdersEvent()

    object OnNextDateClick : OrdersEvent()

    object OnPreviousDateClick : OrdersEvent()
}

sealed class NavigationEvent {
    object GoBack : NavigationEvent()
}