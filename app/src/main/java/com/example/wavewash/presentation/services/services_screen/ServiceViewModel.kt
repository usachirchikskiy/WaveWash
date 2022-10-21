package com.example.wavewash.presentation.services.services_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavewash.domain.use_cases.Service
import com.example.wavewash.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ServiceViewModel @Inject
constructor(
    private val service: Service,
) : ViewModel() {

    var state by mutableStateOf(ServiceState())

    private var job: Job? = null

    init {
        getServices()
    }

    fun onTriggerEvent(event: ServiceEvent) {
        when (event) {
            is ServiceEvent.GetServices -> {
                state = state.copy(page = state.page + 1)
                getServices()
            }
            is ServiceEvent.ChangeSearchQueryValue -> {
                changeSearchQueryValue(event.text)
            }
            is ServiceEvent.ReloadServices -> {
                state = state.copy(page = 0, endReached = false, services = listOf())
                getServices()
            }
        }
    }

    private fun getServices() {
        job?.cancel()
        job = service.get_services(state.searchQuery, state.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if(result.data!!.isEmpty()){
                        state = state.copy(endReached = true)
                    }
                    val listAll = state.services.plus(result.data)
                    state = state.copy(services = listAll, isLoading = false)
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
        state = state.copy(searchQuery = text, page = 0, endReached = false, services = listOf())
        getServices()
    }
}