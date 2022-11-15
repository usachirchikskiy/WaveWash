package com.example.wavewash.domain.model

data class Order(
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
