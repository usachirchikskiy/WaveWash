package com.example.wavewash.presentation.services.new_service

import com.example.wavewash.presentation.janitors.new_janitor.NewJanitorEvents

sealed class NewServiceEvent {

    object AddService : NewServiceEvent()

    data class ChangeDurationValue(val duration:String): NewServiceEvent()

    data class ChangePriceValue(val price:String): NewServiceEvent()

    data class ChangeNameValue(val name:String): NewServiceEvent()

}