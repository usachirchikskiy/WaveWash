package com.example.wavewash.presentation.orders.new_order

import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.presentation.orders.change_order_screen.ChangeOrderEvent
import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent

sealed class NewOrderEvent {
    object Back : NewOrderEvent()
    object AddOrder : NewOrderEvent()
    data class ChangeCarModel(val carModel:String): NewOrderEvent()
    data class ChangeCarNumber(val carNumber:String): NewOrderEvent()
    data class ChangeClientName(val clientName:String): NewOrderEvent()
    data class ChangeClientNumber(val clientNumber:String): NewOrderEvent()

    object ReloadServices: NewOrderEvent()
    object GetServices: NewOrderEvent()
    data class ChangeService(val service: ServiceAnswerDto) : NewOrderEvent()
    object LoadMoreServices: NewOrderEvent()
    data class OnSearchQueryService(val query:String): NewOrderEvent()
    data class DeleteService(val service: ServiceAnswerDto): NewOrderEvent()

    object ReloadWashers: NewOrderEvent()
    object GetWashers: NewOrderEvent()
    data class ChangeWasher(val washer: WasherAnswerDto) : NewOrderEvent()
    object LoadMoreWashers: NewOrderEvent()
    data class OnSearchQueryWasher(val query:String): NewOrderEvent()
    data class DeleteWasher(val washer:WasherAnswerDto): NewOrderEvent()
}