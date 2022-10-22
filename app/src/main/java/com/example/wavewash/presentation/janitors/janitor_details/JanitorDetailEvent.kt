package com.example.wavewash.presentation.janitors.janitor_details

import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents

sealed class JanitorDetailEvent {
    object GetJanitor:JanitorDetailEvent()

    object ActiveOrders:JanitorDetailEvent()

    object FinishedOrders:JanitorDetailEvent()

    data class ChangeDates(val dateFrom:String,val dateTo:String):JanitorDetailEvent()

    object OnNextDateClick:JanitorDetailEvent()

    object OnPreviousDateClick:JanitorDetailEvent()

    object ReloadOrders:JanitorDetailEvent()


}