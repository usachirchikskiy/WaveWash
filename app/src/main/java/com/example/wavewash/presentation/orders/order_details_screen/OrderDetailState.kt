package com.example.wavewash.presentation.orders.order_details_screen

import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

data class OrderDetailState(
    val id:Long = 0,
    val carModel: String = "",
    val carNumber: String = "",
    val clientName: String = "",
    val clientNumber: Int = 0,
    val services: List<Service> = listOf(),
    val washers: List<Washer> = listOf(),
    val price: Int = 0,
    val priceOfJanitorsStake: Int = 0,
    val duration: String = "0",
    val isLoading: Boolean = false,
    val error: String = "",
    val completed:Boolean = false,
    val isActive:Boolean = false,
)
