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
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.nunitoSans
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.common.theme.KalendarShape
import com.himanshoe.kalendar.common.ui.KalendarDot
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun KalendarDay(
    size: Dp,
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    isPreviousDay: Boolean,
    isBetweenDay: Boolean,
    kalendarEvents: List<KalendarEvent>,
    kalendarSelector: KalendarSelector,
    onDayClick: (LocalDate, List<KalendarEvent>) -> Unit,
) {
    val isDot = kalendarSelector is KalendarSelector.Dot
    val events = kalendarEvents.filter { it.date == date }

    Surface(
        color = if ((isSelected or isBetweenDay or isPreviousDay)&& !isDot) kalendarSelector.selectedColor else kalendarSelector.defaultColor,
        shape = if (!isDot) kalendarSelector.shape else KalendarShape.DefaultRectangle
    ) {
        var localModifier = modifier
            .size(size)
            .clickable { onDayClick(date, events) }

        Column(
            modifier = localModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                maxLines = 1,
                textAlign = TextAlign.End,
                //style = MaterialTheme.typography.body1,
                color = getTextColor(
                    isSelected,
                    isBetweenDay,
                    isPreviousDay,
                    kalendarSelector,
                    events.isNotEmpty()
                ),
                fontFamily = nunitoSans,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (isDot) {
                KalendarDot(
                    kalendarSelector = kalendarSelector,
                    isSelected = isSelected,
                    isToday = isToday
                )
            }
        }
    }
}

private fun getTextColor(
    isSelected: Boolean,
    isBetweenDay: Boolean,
    isPreviousDay: Boolean,
    kalendarSelector: KalendarSelector,
    isEvent: Boolean,
): Color {
    return when {
        isEvent -> kalendarSelector.eventTextColor
        else -> when {
            isSelected or isPreviousDay or isBetweenDay-> kalendarSelector.selectedTextColor
            else -> kalendarSelector.defaultTextColor
        }
    }
}
