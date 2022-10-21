package com.example.wavewash.presentation.janitors.new_janitor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.washer.WasherDto
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewJanitorViewModel @Inject
constructor(
    private val washer: Washer,
) : ViewModel() {

    var state by mutableStateOf(NewJanitorState())
    private var job: Job? = null

    fun onTriggerEvent(event: NewJanitorEvents) {
        when (event) {
            is NewJanitorEvents.AddWasher -> {
                val fieldsIsEmpty = checkFields()
                if(fieldsIsEmpty){
                    //TODO POPUP DIALOG
                }
                else{
                    addWasher()
                }

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
        job?.cancel()
        job = washer.addWasher(body).onEach { result->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false)
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

    private fun changeStakeValue(stake:String){
        state = state.copy(stake = stake)
    }

    private fun changeNameValue(name:String){
        state = state.copy(name = name)
    }

    private fun changePhoneNumberValue(telephoneNumber:String){
        state = state.copy(telephoneNumber = telephoneNumber)
    }

    private fun checkFields(): Boolean {
        if (state.telephoneNumber.isEmpty() || state.stake.isEmpty()
            || state.name.isEmpty()
        ) {
            return true
        }
        return false
    }
}