package com.himanshoe.kalendar.ui.firey

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.wavewash.ui.theme.AppBackground
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.ui.common.KalendarWeekDayNames
import com.himanshoe.kalendar.util.getMonthNameFormatter
import com.himanshoe.kalendar.util.validateMaxDate
import com.himanshoe.kalendar.util.validateMinDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId

private const val DAYS_IN_WEEK = 7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun KalendarMonth(
    selectedDay: LocalDate,
    yearMonth: YearMonth = YearMonth.now(),
    kalendarKonfig: KalendarKonfig,
    errorMessageLogged: (String) -> Unit,
    kalendarSelector: KalendarSelector,
    kalendarEvents: List<KalendarEvent>,
    dateRangeEnabled: Boolean = false,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit = {}
) {
    val previousClickedDay = remember { mutableStateOf(LocalDate.now(ZoneId.of("Asia/Tashkent"))) }
    Column(
        modifier = Modifier
            .background(AppBackground)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        val haptic = LocalHapticFeedback.current

        val monthState = remember {
            mutableStateOf(yearMonth)
        }
        val clickedDay = remember {
            mutableStateOf(selectedDay)
        }

        KalendarHeader(
            //kalendarSelector = kalendarSelector,
            text = monthState.value.format(getMonthNameFormatter(kalendarKonfig.locale)),
            onPreviousMonthClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val year = monthState.value.year
                val isLimitAttached = year.validateMinDate(kalendarKonfig.yearRange.min)
                if (isLimitAttached) {
                    monthState.value = monthState.value.minusMonths(1)
                } else {
                    errorMessageLogged("Minimum year limit reached")
                }
            },
            onNextMonthClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val year = monthState.value.year
                val isLimitAttached = year.validateMaxDate(kalendarKonfig.yearRange.max)
                if (isLimitAttached) {
                    monthState.value = monthState.value.plusMonths(1)
                } else {
                    errorMessageLogged("Minimum year limit reached")
                }
            },
        )
        KalendarWeekDayNames(kalendarKonfig = kalendarKonfig)

        val days: List<LocalDate> = getDays(monthState)

        days.chunked(DAYS_IN_WEEK).forEach { weekDays ->
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val size = (maxWidth / DAYS_IN_WEEK)
                Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    weekDays.forEach { localDate ->
                        val isFromCurrentMonth = YearMonth.from(localDate) == monthState.value
                        if (isFromCurrentMonth) {
                            val isSelected = monthState.value.month == clickedDay.value.month &&
                                monthState.value.year == clickedDay.value.year &&
                                localDate == clickedDay.value
                            val isPreviousDay = monthState.value.month == clickedDay.value.month && monthState.value.year == clickedDay.value.year && localDate == previousClickedDay.value
                            val isBetweenDay = monthState.value.month == clickedDay.value.month && monthState.value.year == clickedDay.value.year && (localDate > previousClickedDay.value && localDate < clickedDay.value || localDate < previousClickedDay.value && localDate > clickedDay.value)

                            KalendarDay(
                                size = size,
                                date = localDate,
                                isSelected = isSelected,
                                isToday = localDate == LocalDate.now(ZoneId.of("Asia/Tashkent")),
                                isPreviousDay = if (dateRangeEnabled) isPreviousDay else false,
                                isBetweenDay = if (dateRangeEnabled) isBetweenDay else false,
                                kalendarSelector = kalendarSelector,
                                kalendarEvents = kalendarEvents,
                                onDayClick = { date, events ->
                                    if(date<=LocalDate.now(ZoneId.of("Asia/Tashkent"))) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        previousClickedDay.value = clickedDay.value
                                        clickedDay.value = date
                                        onDateRangeSelected(previousClickedDay.value to date)
                                    }
                                }
                            )
                        } else {
                            KalendarEmptyDay(modifier = Modifier.size(size))
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDays(monthState: MutableState<YearMonth>): List<LocalDate> {
    return mutableListOf<LocalDate>().apply {
        val firstDay = monthState.value.atDay(1)
        val firstSunday = if (firstDay.dayOfWeek == java.time.DayOfWeek.SUNDAY) {
            firstDay
        } else {
            firstDay.minusDays(firstDay.dayOfWeek.value.toLong())
        }
        repeat(6) { weekIndex ->
            (0..6).forEach { dayIndex ->
                add(firstSunday.plusDays((7 * weekIndex + dayIndex).toLong()))
            }
        }
    }
}
