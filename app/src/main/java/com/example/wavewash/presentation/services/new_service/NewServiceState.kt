package com.example.wavewash.presentation.services.new_service

data class NewServiceState(
    val name:String = "",
    val price:String = "",
    val duration:String = "",
    val isLoading:Boolean = false,
    val error:String = "",

    val nameError:Int? = null,
    val priceError:Int? = null,
    val durationError:Int? = null
)