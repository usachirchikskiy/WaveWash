package com.example.wavewash.data.remote.dto.order

import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto

data class OrderAnswerDto(
    val id:Long,
    val active: Boolean,
    val cancelled: Boolean,//TODO DELETE FIELD
    val carModel: String,
    val carNumber: String,
    val clientName: String,
    val clientNumber: Int,
    val date: Long,
    val price: Int,
    val services: List<ServiceAnswerDto>,
    val washers: List<WasherAnswerDto>
)