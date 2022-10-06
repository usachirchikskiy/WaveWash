package com.example.wavewash.presentation.janitors.new_janitor

data class NewJanitorState(
    val name:String = "",
    val telephoneNumber:String = "",
    val stake:String = "",
    val isLoading:Boolean = false,
    val error:String = ""
)