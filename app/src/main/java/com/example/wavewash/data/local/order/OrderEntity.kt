package com.example.wavewash.data.local.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wavewash.domain.model.Order

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = false)
    val orderId: Long,
    val price: Int,
    val carModel: String,
    val carNumber: String,
    val clientName: String,
    val clientNumber: Int,
    val active: Boolean,
//    val cancelled: Boolean,
//    val cancelledReason: String,
    val date: Long
)

fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        orderId = id,
        price = price,
        carModel = carModel,
        carNumber = carNumber,
        clientName = clientName,
        clientNumber = clientNumber,
        active = active,
//        cancelled = cancelled,
//        cancelledReason = cancelledReason,
        date = date
    )
}
