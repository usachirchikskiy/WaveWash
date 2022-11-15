package com.example.wavewash.presentation.janitors.janitors_screen

import com.example.wavewash.domain.model.Washer

data class JanitorState(
    val washers:List<Washer> = listOf(),
    val searchQuery:String = "",
    val page:Int = 0,
    val isLoading:Boolean = false,
    val error:String = "",
    val endReached:Boolean = false
)