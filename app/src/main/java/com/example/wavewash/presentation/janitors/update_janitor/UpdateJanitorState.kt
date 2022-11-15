package com.example.wavewash.presentation.janitors.update_janitor

import android.net.Uri

data class UpdateJanitorState(
    val id:Long = -1,
    val name:String = "",
    val telephoneNumber:String = "",
    val stake:String = "",
    val imageUrl:String = "",
    val uri: Uri? = null,
    val isLoading:Boolean = false,
    val error:String = ""
)