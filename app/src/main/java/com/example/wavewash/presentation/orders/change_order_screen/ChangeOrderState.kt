package com.example.wavewash.presentation.orders.change_order_screen

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

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
    val services: List<Service> = listOf(),
    val washers: List<Washer> = listOf(),

    val servicesOfDialog:List<Service> = listOf(),
    val serviceEndIsReached:Boolean = false,
    val serviceIsLoading: Boolean = false,
    val serviceSearchQuery:String = "",
    val servicePage:Int = 0,

    val washersOfDialog:List<Washer> = listOf(),
    val washerEndIsReached: Boolean = false,
    val washerIsLoading: Boolean = false,
    val washerSearchQuery:String = "",
    val washerPage:Int = 0,

    val orderIsLoading: Boolean = false,
    val error: String = "",

    val washersError:Int? = null,
    val servicesError:Int? = null,
    val carNumberError:Int? = null,
    val clientNameError:Int? = null,
    val clientTelephoneNumberError:Int? = null,
)