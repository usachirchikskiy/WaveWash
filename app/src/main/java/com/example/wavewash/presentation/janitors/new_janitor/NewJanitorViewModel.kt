package com.example.wavewash.presentation.janitors.new_janitor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.WasherDto
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorState
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewJanitorViewModel @Inject
constructor(
    private val washer: Washer,
) : ViewModel() {

    var state by mutableStateOf(NewJanitorState())

    fun onTriggerEvent(event: NewJanitorEvents) {
        when (event) {
            is NewJanitorEvents.AddWasher -> {
                addWasher()
            }
            is NewJanitorEvents.ChangeStakeValue ->{
                changeStakeValue(event.stake)
            }
            is NewJanitorEvents.ChangeNameValue ->{
                changeNameValue(event.text)
            }
            is NewJanitorEvents.ChangePhoneNumberValue ->{
                changePhoneNumberValue(event.phoneNumber)
            }
        }
    }

    private fun addWasher(){
        val body = WasherDto(state.name,state.stake.toInt(),state.telephoneNumber.toInt())
        washer.addWasher(body).onEach { result->
            when (result) {
                is Resource.Success -> {
//                    state = state.copy(washers = result.data!!)
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

    private fun changeStakeValue(stake:Int){
        state = state.copy(stake = stake.toString())
    }

    private fun changeNameValue(name:String){
        state = state.copy(name = name)
    }

    private fun changePhoneNumberValue(telephoneNumber:Int){
        state = state.copy(telephoneNumber = telephoneNumber.toString())
    }
}