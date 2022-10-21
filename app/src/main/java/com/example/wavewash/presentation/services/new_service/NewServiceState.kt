package com.example.wavewash.presentation.services.new_service

data class NewServiceState(
    val name:String = "",
    val price:String = "",
    val duration:String = "",
    val changeCompleted:Boolean = false,
    val isLoading:Boolean = false,
    val error:String = ""
)