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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.getFromAndTo
import com.example.wavewash.utils.getNextDate
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CalendarDialog(
    openDialogCustom: MutableState<Boolean>,
    dateRangeSelected: (String, String) -> Unit
) {
    val dateFrom = remember { mutableStateOf("") }
    val dateTo = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            openDialogCustom.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Box(
            modifier = Modifier
                .size(450.dp, 600.dp)
                .clip(Shapes.medium)
                .background(AppBackground)
                .padding(20.dp)
        ) {
            Kalendar(
                kalendarType = KalendarType.Firey(),
                dateRangeEnabled = true,
                onDateRangeSelected = {
//                    dateFrom.value = it.first.toString()
//                    dateTo.value = it.second.toString()
                    if(it.first.toString()>it.second.toString()){
                      dateFrom.value = it.second.toString()
                      dateTo.value = it.first.toString()//getNextDate(it.first.toString())
                    }
                    else{
                        dateFrom.value = it.first.toString()
                        dateTo.value = it.second.toString()//getNextDate(it.second.toString())
                    }
                },
                onReadyClick = {
                    if(dateFrom.value =="" && dateTo.value == ""){
                        val pair = getFromAndTo()
                        dateFrom.value = pair.first
                        dateTo.value = pair.first
                    }
                    dateRangeSelected.invoke(dateFrom.value, dateTo.value)
                    openDialogCustom.value = false
                },
                onCancelled = {
                    openDialogCustom.value = false
                }
            )
        }
    }
}