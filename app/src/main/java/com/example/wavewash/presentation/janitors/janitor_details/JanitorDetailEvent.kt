package com.example.wavewash.presentation.janitors.janitor_details


sealed class JanitorDetailEvent {
    object GetJanitor:JanitorDetailEvent()

    object GetJanitorOrders:JanitorDetailEvent()

    object ActiveOrders:JanitorDetailEvent()

    object FinishedOrders:JanitorDetailEvent()

    data class ChangeDates(val dateFrom:String,val dateTo:String):JanitorDetailEvent()

    object OnNextDateClick:JanitorDetailEvent()

    object OnPreviousDateClick:JanitorDetailEvent()

//    object ReloadWasher:JanitorDetailEvent()
//
//    object ReloadOrders:JanitorDetailEvent()


}