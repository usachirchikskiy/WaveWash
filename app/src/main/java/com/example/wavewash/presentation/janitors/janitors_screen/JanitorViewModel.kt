package com.example.wavewash.presentation.janitors.janitors_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.remote.dto.LoginDto
import com.example.wavewash.data.remote.dto.WasherAnswerDto
import com.example.wavewash.data.remote.dto.WasherDto
import com.example.wavewash.domain.use_cases.Login
import com.example.wavewash.domain.use_cases.Washer
import com.example.wavewash.presentation.login.LoginEvents
import com.example.wavewash.presentation.login.LoginState
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "JanitorViewModel"
@HiltViewModel
class JanitorViewModel @Inject
constructor(
    private val washer: Washer
) : ViewModel() {

    var state by mutableStateOf(JanitorState())

    private var job: Job? = null

    init {
        getWashers()
    }

    fun onTriggerEvent(event: JanitorEvents) {
        when (event) {
            is JanitorEvents.GetWashers -> {
                state = state.copy(page = state.page + 1)
                getWashers()
            }
            is JanitorEvents.ChangeSearchQueryValue -> {
                changeSearchQueryValue(event.text)
            }
            is JanitorEvents.ReloadWashers -> {
                getWashers()
            }
        }
    }

    private fun getWashers() {
        job?.cancel()
        job = washer.get_washers(state.searchQuery, state.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if(result.data!!.isEmpty()){
                        state = state.copy(endReached = true)
                    }
                    val listAll = state.washers.plus(result.data)
                    state = state.copy(washers = listAll, isLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message!!, isLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeSearchQueryValue(text:String){
        state = state.copy(searchQuery = text, page = 0, endReached = false, washers = listOf())
        getWashers()
    }
}