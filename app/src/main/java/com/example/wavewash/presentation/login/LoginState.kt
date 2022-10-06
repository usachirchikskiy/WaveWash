package com.example.wavewash.presentation.login

import com.example.wavewash.data.remote.dto.LoginDto

data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val changeScreen:Boolean = false,
    val isPasswordVisible:Boolean = false
)