package com.example.wavewash.data.local.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["orderId","washerId"])
data class OrderWasherCrossRef(
    val orderId:Long,
    val washerId:Long
)