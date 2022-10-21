package com.example.wavewash.presentation.janitors.update_janitor

data class UpdateJanitorState(
    val id:Long = -1,
    val name:String = "",
    val telephoneNumber:String = "",
    val stake:String = "",
    val isLoading:Boolean = false,
    val error:String = ""
)