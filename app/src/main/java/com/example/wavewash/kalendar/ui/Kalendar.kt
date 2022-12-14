package com.himanshoe.kalendar.ui
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.common.theme.KalendarShape
import com.himanshoe.kalendar.ui.KalendarType.Firey
import com.himanshoe.kalendar.ui.KalendarType.Oceanic
import com.himanshoe.kalendar.ui.firey.KalendarFirey
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit
import java.util.*

/**
 * [KalendarType] is used to distinguish the type of calendar
 * [Firey] represents the Monthly View
 * [Oceanic] represents the Week View
 */
@RequiresApi(Build.VERSION_CODES.O)
sealed class KalendarType {
    data class Firey(val shape: Shape = KalendarShape.SelectedShape) : KalendarType()
    data class Oceanic(
        val shape: Shape = KalendarShape.ButtomCurvedShape,
        val startDate: LocalDate = LocalDate.now(),
    ) : KalendarType()
}

/**
 * [Kalendar] is exposed to be used as composable
 * @param kalendarType is the type of calendar.See [KalendarType]
 * @param kalendarStyle sets the style of calendar.See [KalendarStyle]
 * @param kalendarKonfig is user to setup config needed for rendering calendar.See [KalendarKonfig]
 * @param selectedDay is representation for selected marker.
 * @param onCurrentDayClick gives the day click listener
 * @param errorMessage gives the error message if any
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Kalendar(
    kalendarType: KalendarType,
    kalendarKonfig: KalendarKonfig = KalendarKonfig(),
    kalendarStyle: KalendarStyle = KalendarStyle(),
    selectedDay: LocalDate,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    errorMessage: (String) -> Unit = {},
    dateRangeEnabled: Boolean = false,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit = {},
    onReadyClick:()->Unit = {},
    onCancelled:()->Unit = {}
) {
    val shape =
        if (kalendarStyle.hasRadius) KalendarShape.SelectedShape else KalendarShape.DefaultRectangle

    when (kalendarType) {
        is Firey -> KalendarFirey(
            kalendarKonfig,
            kalendarStyle.copy(shape = shape),
            selectedDay,
            kalendarEvents,
            errorMessage,
            dateRangeEnabled,
            onDateRangeSelected,
            onReadyClick,
            onCancelled
        )
    }
}
