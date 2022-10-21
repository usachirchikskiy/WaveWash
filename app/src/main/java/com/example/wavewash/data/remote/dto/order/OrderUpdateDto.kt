package com.example.wavewash.data.remote.dto.order

data class OrderUpdateDto(
    val cancelled: Boolean,
    val cancelledReason: String,
    val active:Boolean,
    val clientNumber: Int,
    val clientName:String,
    val carModel: String,
    val carNumber: String,
    val serviceIds: List<Long>,
    val washerIds: List<Long>
)