package com.example.wavewash.presentation.services.update_service

import com.example.wavewash.presentation.janitors.update_janitor.UpdateJanitorEvents
import com.example.wavewash.presentation.services.new_service.NewServiceEvent

sealed class UpdateServiceEvent {
    data class GetService(val id:Long): UpdateServiceEvent()

    object UpdateService : UpdateServiceEvent()

    data class ChangeDurationValue(val duration: String) : UpdateServiceEvent()

    data class ChangePriceValue(val price: String) : UpdateServiceEvent()

    data class ChangeNameValue(val name: String) : UpdateServiceEvent()

}