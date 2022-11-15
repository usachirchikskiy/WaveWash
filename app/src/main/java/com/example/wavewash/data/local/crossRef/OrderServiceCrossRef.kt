package com.example.wavewash.data.local.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["orderId","serviceId"])
data class OrderServiceCrossRef(
    val orderId:Long,
    val serviceId:Long
)