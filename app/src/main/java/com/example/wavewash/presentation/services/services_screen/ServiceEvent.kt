package com.example.wavewash.presentation.services.services_screen

import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents

sealed class ServiceEvent {
    object GetFirstPage:ServiceEvent()

    object GetServices : ServiceEvent()

    data class ChangeSearchQueryValue(val text:String) : ServiceEvent()

    object ReloadServices : ServiceEvent()

    object NewServiceClicked : ServiceEvent()
}