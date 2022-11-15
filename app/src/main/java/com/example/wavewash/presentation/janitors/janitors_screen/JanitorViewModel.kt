package com.example.wavewash.presentation.janitors.janitors_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.data.local.washer.toWasher
import com.example.wavewash.domain.use_cases.WasherUseCase
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "JanitorViewModel"

@HiltViewModel
class JanitorViewModel @Inject
constructor(
    private val washerUseCase: WasherUseCase
) : ViewModel() {

    var state by mutableStateOf(JanitorState())

    private var job: Job? = null

    init {
        onTriggerEvent(JanitorEvents.GetWashersFirstPage)
    }

    fun onTriggerEvent(event: JanitorEvents) {
        when (event) {
            is JanitorEvents.GetWashersFirstPage -> {
                getWashers()
            }
            is JanitorEvents.GetWashers -> {
                state = state.copy(page = state.page + 1)
                getWashers()
            }
            is JanitorEvents.ChangeSearchQueryValue -> {
                changeSearchQueryValue(event.text)
                getWashers()
            }
//            is JanitorEvents.ReloadWashers -> {
//                state = state.copy(page = 0, endReached = false, washers = listOf())
//                getWashers()
//            }
//            is JanitorEvents.NewJanitorClicked -> {
//                state = state.copy(searchQuery = "",page = 0, endReached = false)
//            }
        }
    }

    private fun getWashers() {
        job?.cancel()
        job = washerUseCase.get_washers(state.searchQuery, state.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (state.washers == result.data) {
                        state = state.copy(endReached = true)
                    } else {
                        state = state.copy(washers = result.data!!)
                    }
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

    private fun changeSearchQueryValue(text: String) {
        state = state.copy(searchQuery = text, page = 0, endReached = false, washers = listOf())
    }
}