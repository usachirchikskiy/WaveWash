package com.example.wavewash.presentation.orders.new_order

import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto

data class NewOrderState(
    val clientNumber: String = "",
    val clientName: String = "",
    val carModel: String = "",
    val carNumber: String = "",
    val services: List<ServiceAnswerDto> = listOf(),
    val washers: List<WasherAnswerDto> = listOf(),
    val duration:String = "0",
    val price:Int = 0,
    val priceOfJanitorsStake:Int = 0,

    val servicesOfDialog:List<ServiceAnswerDto> = listOf(),
    val serviceEndIsReached:Boolean = false,
    val serviceIsLoading: Boolean = false,
    val serviceSearchQuery:String = "",
    val servicePage:Int = 0,

    val washersOfDialog:List<WasherAnswerDto> = listOf(),
    val washerEndIsReached: Boolean = false,
    val washerIsLoading: Boolean = false,
    val washerSearchQuery:String = "",
    val washerPage:Int = 0,

    val orderIsLoading:Boolean = false,
    val error:String = "",

    val requiredFields:Boolean = false,
    val changeCompleted: Boolean = false
)