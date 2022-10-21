package com.example.wavewash.presentation.login

sealed class LoginEvents{

    data class Login(
        val email: String,
        val login: String
    ): LoginEvents()

    object ChangePasswordVisibility: LoginEvents()

    data class ChangeEmailValue(val text:String): LoginEvents()

    data class ChangePasswordValue(val text:String): LoginEvents()

    object ChangeChangeScreenValue: LoginEvents()

}