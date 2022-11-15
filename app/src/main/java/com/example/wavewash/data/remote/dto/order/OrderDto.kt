package com.example.wavewash.data.remote.dto.order

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.data.remote.dto.washer.WasherDto
import com.example.wavewash.domain.model.Order
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

data class OrderDto(
    val id: Long,
    val active: Boolean,
    val cancelled: Boolean,//TODO DELETE FIELD
    val cancelledReason: String,
    val carModel: String,
    val carNumber: String,
    val clientName: String,
    val clientNumber: Int,
    val date: Long,
    val price: Int,
    val services: List<Service>,
    val washers: List<Washer>
)

fun OrderDto.toOrder(): Order {
    return Order(
        id = id,
        active = active,
        cancelled = cancelled,
        carModel = carModel,
        carNumber = carNumber,
        clientName = clientName,
        clientNumber = clientNumber,
        date = date,
        price = price,
        cancelledReason = cancelledReason,
        services = services,
        washers = washers
    )
}

