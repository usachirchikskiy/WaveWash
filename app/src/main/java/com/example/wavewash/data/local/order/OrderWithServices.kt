package com.example.wavewash.data.local.order

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.service.ServiceEntity
import com.example.wavewash.data.local.service.toService
import com.example.wavewash.data.local.washer.WasherEntity
import com.example.wavewash.data.local.washer.toWasher
import com.example.wavewash.domain.model.Order

data class OrderWithServices(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "serviceId",
        associateBy = Junction(OrderServiceCrossRef::class)
    )
    val services: List<ServiceEntity>
)

fun OrderWithServices.toOrder(washer:WasherEntity): Order {
    return Order(
        id = order.orderId,
        active = order.active,
//        cancelled = order.cancelled,//TODO DELETE FIELD
//        cancelledReason=order.cancelledReason,
        carModel=order.carModel,
        carNumber = order.carNumber,
        clientName = order.clientName,
        clientNumber = order.clientNumber,
        date = order.date,
        price = order.price,
        services = services.map { it.toService() },
        washers = listOf(washer.toWasher())
    )
}