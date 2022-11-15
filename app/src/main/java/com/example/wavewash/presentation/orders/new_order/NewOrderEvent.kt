package com.example.wavewash.presentation.orders.new_order

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

sealed class NewOrderEvent {
    object AddOrder : NewOrderEvent()
    data class ChangeCarModel(val carModel:String): NewOrderEvent()
    data class ChangeCarNumber(val carNumber:String): NewOrderEvent()
    data class ChangeClientName(val clientName:String): NewOrderEvent()
    data class ChangeClientNumber(val clientNumber:String): NewOrderEvent()
    data class WasherOrderOrNot(val washerId:Long):NewOrderEvent()

    object ReloadServices: NewOrderEvent()
    object GetServices: NewOrderEvent()
    data class ChangeService(val service: Service) : NewOrderEvent()
    object LoadMoreServices: NewOrderEvent()
    data class OnSearchQueryService(val query:String): NewOrderEvent()
    data class DeleteService(val service: Service): NewOrderEvent()

    object ReloadWashers: NewOrderEvent()
    object GetWashers: NewOrderEvent()
    data class ChangeWasher(val washer: Washer) : NewOrderEvent()
    object LoadMoreWashers: NewOrderEvent()
    data class OnSearchQueryWasher(val query:String): NewOrderEvent()
    data class DeleteWasher(val washer:Washer): NewOrderEvent()
}