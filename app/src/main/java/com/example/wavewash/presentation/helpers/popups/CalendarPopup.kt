package com.example.wavewash.presentation.helpers.popups

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.Shapes
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CalendarDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = {
            println("onDismiss")
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Box(
            modifier = Modifier
                .size(450.dp,600.dp)
                .clip(Shapes.medium)
                .background(AppBackground)
                .padding(20.dp)
        ) {
            Kalendar(
                kalendarType = KalendarType.Firey(),
                dateRangeEnabled = true,
                onDateRangeSelected = {
                    println("Between ${it.first} \n ${it.second}")
                },
                onReadyClick = {
                    openDialogCustom.value = false //TODO Change
                },
                onCancelled = {
                    openDialogCustom.value = false
                }
            )
        }
    }
}