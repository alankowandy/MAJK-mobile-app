package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.DayOfWeek
import org.example.majk.core.presentation.theme.Cyan
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.LightGray
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleState

@Composable
fun PolishMonthCalendar(
    modifier: Modifier = Modifier,
    state: ScheduleState,
    onMonthOffsetChange: (Int) -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {

    Column(
        modifier = modifier
            .padding(vertical = 10.dp)
    ) {
        HorizontalCalendarView(
            startDate = state.selectedDate
        ) { monthOffset ->

            LaunchedEffect(monthOffset) {
                onMonthOffsetChange(monthOffset)
            }

            CalendarView(
                config = rememberCalendarState(
                    startDate = state.selectedDate,
                    monthOffset = monthOffset
                ),
                day = { dayState ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(
                                if (dayState.isActiveDay) Cyan
                                else Color.Transparent
                            )
                            .clickable { onDateSelected(dayState.date) }
                    ) {
                        Text(
                            text  = dayState.date.dayOfMonth.toString(),
                            color = if (dayState.isForNextMonth || dayState.isForPreviousMonth) LightGray
                            else Color.Unspecified
                        )
                    }
                },
                header = { month, year ->
                    val monthName = when (month) {
                        Month.JANUARY   -> "Styczeń"
                        Month.FEBRUARY  -> "Luty"
                        Month.MARCH     -> "Marzec"
                        Month.APRIL     -> "Kwiecień"
                        Month.MAY       -> "Maj"
                        Month.JUNE      -> "Czerwiec"
                        Month.JULY      -> "Lipiec"
                        Month.AUGUST    -> "Sierpień"
                        Month.SEPTEMBER -> "Wrzesień"
                        Month.OCTOBER   -> "Październik"
                        Month.NOVEMBER  -> "Listopad"
                        Month.DECEMBER  -> "Grudzień"
                        else -> ""
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$monthName $year",
                            color = DarkTeal,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                dayOfWeekLabel = { dayOfWeek ->
                    val dayName = when (dayOfWeek.name) {
                        DayOfWeek.MONDAY.name -> "Poniedziałek"
                        DayOfWeek.TUESDAY.name -> "Wtorek"
                        DayOfWeek.WEDNESDAY.name -> "Środa"
                        DayOfWeek.THURSDAY.name -> "Czwartek"
                        DayOfWeek.FRIDAY.name -> "Piątek"
                        DayOfWeek.SATURDAY.name -> "Sobota"
                        DayOfWeek.SUNDAY.name -> "Niedziela"
                        else -> ""
                    }

                    Text(
                        text = dayName.take(2),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = DarkTeal
                    )
                }
            )

        }
    }
}