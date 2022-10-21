package com.example.wavewash.presentation.services.update_service

data class UpdateServiceState(
    val id:Long = -1,
    val name:String = "",
    val price:String = "",
    val duration:String = "",
    val changeCompleted:Boolean = false,
    val isLoading:Boolean = false,
    val error:String = ""
)