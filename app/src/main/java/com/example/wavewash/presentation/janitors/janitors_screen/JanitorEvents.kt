package com.example.wavewash.presentation.janitors.janitors_screen

import com.example.wavewash.presentation.login.LoginEvents


sealed class JanitorEvents {

    object GetWashers : JanitorEvents()

    data class ChangeSearchQueryValue(val text:String) : JanitorEvents()

    object ReloadWashers : JanitorEvents()


}