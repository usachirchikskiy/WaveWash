package com.example.wavewash.presentation.orders.order_details_screen

import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent

sealed class OrderDetailEvent{
    data class GetOrder(val orderId:Long) : OrderDetailEvent()

    object CompleteOrder : OrderDetailEvent()
//
//    object FinishedOrders : OrdersEvent()
//
//    data class ChangeDates(val dateFrom:String,val dateTo:String) : OrdersEvent()
//
//    object ReloadOrders : OrdersEvent()
//
//    object OnNextDateClick: OrdersEvent()
//
//    object OnPreviousDateClick: OrdersEvent()
}
