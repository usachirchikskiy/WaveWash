package com.example.wavewash.presentation.janitors.janitors_screen

import com.example.wavewash.presentation.login.LoginEvents


sealed class JanitorEvents {

//    data class Login(
//        val email: String,
//        val login: String
//    ) : JanitorEvents()

    object GetWashers : JanitorEvents()

    data class ChangeSearchQueryValue(val text:String) : JanitorEvents()

    object ReloadWashers : JanitorEvents()


//    data class ChangeEmailValue(val text: String) : JanitorEvents()
//
//    data class ChangePasswordValue(val text: String) : JanitorEvents()
//
//    object ChangeChangeScreenValue : JanitorEvents()


}