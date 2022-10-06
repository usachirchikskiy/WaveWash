package com.himanshoe.kalendar.ui.firey
/*
* MIT License
*
* Copyright (c) 2022 Himanshu Singh
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANT1IES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.common.theme.KalendarTheme
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun KalendarFirey(
    kalendarKonfig: KalendarKonfig = KalendarKonfig(),
    kalendarStyle: KalendarStyle = KalendarStyle(),
    selectedDay: LocalDate = LocalDate.now(),
    kalendarEvents: List<KalendarEvent>,
    errorMessageLogged: (String) -> Unit,
    dateRangeEnabled: Boolean = false,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit = {},
    onReadyClick: () -> Unit,
    onCancelled:()->Unit
) {

    KalendarTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground),
        ) {
            KalendarView(
                kalendarSelector = kalendarStyle.kalendarSelector,
                kalendarKonfig = kalendarKonfig,
                errorMessageLogged = errorMessageLogged,
                selectedDay = selectedDay,
                kalendarEvents = kalendarEvents,
                dateRangeEnabled = dateRangeEnabled,
                onDateRangeSelected = onDateRangeSelected
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(Shapes.medium)
                        .clickable {
                            onCancelled.invoke()
                        }
                        .padding(horizontal = 42.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = ComposeString.resource(R.string.cancel).value(),
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(Shapes.medium)
                        .background(ActiveButtonBackground)
                        .clickable {
                            onReadyClick.invoke()
                        }
                        .padding(horizontal = 42.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = ComposeString.resource(R.string.ready).value(),
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
