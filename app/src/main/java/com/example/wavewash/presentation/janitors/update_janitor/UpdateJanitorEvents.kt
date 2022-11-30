package com.example.wavewash.presentation.janitors.update_janitor

import android.net.Uri

sealed class UpdateJanitorEvents {

    data class GetWasher(val id:Long):UpdateJanitorEvents()

    object DeleteWasher: UpdateJanitorEvents()

    object UpdateWasher : UpdateJanitorEvents()

    data class ChangeNameValue(val text:String):UpdateJanitorEvents()

    data class ChangeStakeValue(val stake:String):UpdateJanitorEvents()

    data class ChangePhoneNumberValue(val phoneNumber: String):UpdateJanitorEvents()

    data class GalleryImage(val uri: Uri):UpdateJanitorEvents()

}