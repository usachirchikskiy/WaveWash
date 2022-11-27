package com.example.wavewash.presentation.orders.new_order

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

data class NewOrderState(
    val clientNumber: String = "",
    val clientName: String = "",
    val carModel: String = "",
    val carNumber: String = "",
    val services: List<Service> = listOf(),
    val washers: List<Washer> = listOf(),
    val duration:String = "0",
    val price:Int = 0,
    val priceOfJanitorsStake:Int = 0,
    val washerId:Long = -1L,

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

    val orderIsLoading:Boolean = false,
    val error:String = "",

    val washersError:Int? = null,
    val servicesError:Int? = null,
    val carNumberError:Int? = null,
    val clientNameError:Int? = null,
    val clientTelephoneNumberError:Int? = null,

//    val requiredFields:Boolean = false
)