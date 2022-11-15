package com.example.wavewash.presentation.janitors.new_janitor

import android.net.Uri

data class NewJanitorState(
    val uri: Uri? = null,
    val name:String = "",
    val telephoneNumber:String = "",
    val stake:String = "",
    val isLoading:Boolean = false,
    val error:String = ""
)