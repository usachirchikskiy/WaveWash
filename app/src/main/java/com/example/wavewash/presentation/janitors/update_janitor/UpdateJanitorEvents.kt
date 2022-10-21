package com.example.wavewash.presentation.janitors.update_janitor

sealed class UpdateJanitorEvents {

    data class GetWasher(val id:Long):UpdateJanitorEvents()

    object UpdateWasher : UpdateJanitorEvents()

    data class ChangeNameValue(val text:String):UpdateJanitorEvents()

    data class ChangeStakeValue(val stake:Int):UpdateJanitorEvents()

    data class ChangePhoneNumberValue(val phoneNumber: Int):UpdateJanitorEvents()

}