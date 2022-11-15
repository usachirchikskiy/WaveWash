package com.example.wavewash.presentation.services.services_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.ServiceUseCase
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ServiceViewModel"

@HiltViewModel
class ServiceViewModel @Inject
constructor(
    private val serviceUseCase: ServiceUseCase,
) : ViewModel() {

    var state by mutableStateOf(ServiceState())

    private var job: Job? = null

    init {
        onTriggerEvent(ServiceEvent.GetFirstPage)
    }

    fun onTriggerEvent(event: ServiceEvent) {
        when (event) {
            is ServiceEvent.GetFirstPage -> {
                getServices()
            }
            is ServiceEvent.GetServices -> {
                state = state.copy(page = state.page + 1)
                getServices()
            }
            is ServiceEvent.ChangeSearchQueryValue -> {
                changeSearchQueryValue(event.text)
                getServices()
            }
//            is ServiceEvent.ReloadServices -> {
//                state = state.copy(page = 0, endReached = false, services = listOf())
//                getServices()
//            }
//            is ServiceEvent.NewServiceClicked -> {
//                state = state.copy(searchQuery = "",page = 0, endReached = false,)
//            }
        }
    }


    private fun getServices() {
        job?.cancel()
        job = serviceUseCase.get_services(state.searchQuery, state.page)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (state.services == result.data) {
                            state = state.copy(endReached = true)
                        } else {
                            state = state.copy(services = result.data!!)
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
        state = state.copy(searchQuery = text, page = 0, endReached = false, services = listOf())
    }

}