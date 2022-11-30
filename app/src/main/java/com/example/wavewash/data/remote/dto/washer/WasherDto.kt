package com.example.wavewash.data.remote.dto.washer

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

data class WasherDto (
    val id:Long,
    val name:String,
    val telephoneNumber:String,
    val active:Boolean,
    val image:String,
    val stake:Int,
    val deleted:Boolean
)

fun WasherDto.toWasher(): Washer{
    return Washer(
        id = id,
        name = name,
        telephoneNumber = telephoneNumber,
        active = active,
        image = image,
        stake = stake,
        deleted = deleted
    )
}

fun Washer.toWasherDto(): WasherDto {
    return WasherDto(
        id = id,
        name = name,
        telephoneNumber = telephoneNumber,
        active = active,
        image = image,
        stake = stake,
        deleted = deleted
    )
}