package com.example.wavewash.data.remote.dto.service

import com.example.wavewash.domain.model.Service

data class ServiceDto(
    val id:Long,
    val duration: Int,
    val name: String,
    val price: Int,
    val deleted:Boolean
)


fun ServiceDto.toService(): Service {
    return Service(
        id = id,
        name = name,
        duration = duration,
        price = price,
        deleted = deleted
    )
}

fun Service.toServiceDto(): ServiceDto {
    return ServiceDto(
        id = id,
        name = name,
        duration = duration,
        price = price,
        deleted = deleted
    )
}