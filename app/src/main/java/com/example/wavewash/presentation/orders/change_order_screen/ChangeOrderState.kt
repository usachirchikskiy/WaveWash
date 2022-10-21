package com.example.wavewash.presentation.orders.change_order_screen

import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto

data class ChangeOrderState(
    val id:Long = 0,
    val cancelled:Boolean = false,
    val cancelledReason:String = "",
    val clientNumber: String = "",
    val clientName: String = "",
    val carModel: String = "",
    val carNumber: String = "",
    val price: Int = 0,
    val priceOfJanitorsStake: Int = 0,
    val duration: String = "0",
    val services: List<ServiceAnswerDto> = listOf(),
    val washers: List<WasherAnswerDto> = listOf(),

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

    val orderIsLoading: Boolean = false,
    val error: String = "",

    val requiredFields: Boolean = false,
    val changeCompleted: Boolean = false
)