package com.example.wavewash.presentation.services.services_screen

import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto

data class ServiceState(
    val services:List<ServiceAnswerDto> = listOf(),
    val searchQuery:String = "",
    val page:Int = 0,
    val isLoading:Boolean = false,
    val error:String = "",
    val endReached:Boolean = false
)
