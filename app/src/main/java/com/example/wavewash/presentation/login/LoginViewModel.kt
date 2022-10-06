package com.example.wavewash.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.LoginDto
import com.example.wavewash.domain.use_cases.Login
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val login: Login
) : ViewModel() {
    var state by mutableStateOf(LoginState())

    private var job: Job? = null

    fun onTriggerEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                login(event.email, event.login)
            }
            is LoginEvents.ChangePasswordVisibility -> {
                changePasswordVisibility()
            }
            is LoginEvents.ChangeEmailValue->{
                changeEmailValue(event.text)
            }
            is LoginEvents.ChangePasswordValue->{
                changePasswordValue(event.text)
            }
            is LoginEvents.ChangeChangeScreenValue->{
                changeScreenValue()
            }
        }
    }

    fun login(email: String, password: String) {
        job?.cancel()
        job = login.execute(body = LoginDto(email, password)).onEach { result->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(changeScreen = true)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changePasswordVisibility(){
        val isPasswordVisible = !state.isPasswordVisible
        state = state.copy(isPasswordVisible = isPasswordVisible)
    }

    private fun changeEmailValue(text:String){
        state = state.copy(email = text)
    }

    private fun changePasswordValue(text:String){
        state = state.copy(password = text)
    }

    private fun changeScreenValue(){
        state= state.copy(changeScreen = false)
    }
}
