package com.example.wavewash.presentation.orders.change_order_screen

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer


sealed class ChangeOrderEvent {

    object SaveChanges:ChangeOrderEvent()
    object Back:ChangeOrderEvent()
    object GetOrder : ChangeOrderEvent()
    data class ChangeCarModel(val carModel:String): ChangeOrderEvent()
    data class ChangeCarNumber(val carNumber:String): ChangeOrderEvent()
    data class ChangeClientName(val clientName:String): ChangeOrderEvent()
    data class ChangeClientNumber(val clientNumber:String): ChangeOrderEvent()
    data class CancelOrder(val cancelledReason:String):ChangeOrderEvent()

    object ReloadServices: ChangeOrderEvent()
    object GetServices: ChangeOrderEvent()
    data class ChangeService(val service: Service) : ChangeOrderEvent()
    object LoadMoreServices: ChangeOrderEvent()
    data class OnSearchQueryService(val query:String): ChangeOrderEvent()
    data class DeleteService(val service: Service):ChangeOrderEvent()

    object ReloadWashers:ChangeOrderEvent()
    object GetWashers: ChangeOrderEvent()
    data class ChangeWasher(val washer: Washer) : ChangeOrderEvent()
    object LoadMoreWashers: ChangeOrderEvent()
    data class OnSearchQueryWasher(val query:String): ChangeOrderEvent()
    data class DeleteWasher(val washer:Washer):ChangeOrderEvent()

}