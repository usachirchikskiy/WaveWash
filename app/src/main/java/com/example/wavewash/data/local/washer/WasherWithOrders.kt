package com.example.wavewash.data.local.washer

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.order.OrderEntity
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.service.ServiceEntity

data class WasherWithOrders(
    @Embedded val washer: WasherEntity,
    @Relation(
        parentColumn = "washerId",
        entityColumn = "orderId",
        associateBy = Junction(OrderWasherCrossRef::class)
    )
    val orders: List<OrderEntity>,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "serviceId",
        associateBy = Junction(OrderServiceCrossRef::class)
    )
    val service: List<ServiceEntity>
)