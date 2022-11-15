package com.example.wavewash.presentation.janitors.new_janitor

import android.net.Uri

sealed class NewJanitorEvents {

    object AddWasher : NewJanitorEvents()

    data class ChangeNameValue(val text:String):NewJanitorEvents()

    data class ChangeStakeValue(val stake:String):NewJanitorEvents()

    data class ChangePhoneNumberValue(val phoneNumber: String):NewJanitorEvents()

    data class GalleryImage(val uri: Uri):NewJanitorEvents()
}