package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.isLeapYear
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsState

@Composable
fun DayPicker(
    state: DetailsState,
    onDaySelected: (LocalDate) -> Unit
) {
    fun generateDateRange(start: LocalDate, end: LocalDate): List<LocalDate> {
        val out = mutableListOf<LocalDate>()
        var cur = start
        while (cur <= end) {
            out += cur
            cur = cur.plus(1, DateTimeUnit.DAY)
        }
        return out
    }

    /** how many days in this month for this year */
    fun LocalDate.daysInMonth(): Int = when (monthNumber) {
        1,3,5,7,8,10,12 -> 31
        4,6,9,11        -> 30
        2               -> if (isLeapYear(state.selectedDate.year)) 29 else 28
        else            -> error("impossible month $monthNumber")
    }

    val daysInMonth = remember(state.selectedDate.year, state.selectedDate.month) {
        val start = LocalDate(state.selectedDate.year, state.selectedDate.month, 1)
        val end = start.copy(day = start.daysInMonth())
        generateDateRange(start, end)
    }

    val listState = rememberLazyListState()

    LaunchedEffect(daysInMonth) {
        val index = daysInMonth.indexOfFirst { it == state.selectedDate }
        if (index >= 0 && index > 2) {
            listState.scrollToItem(index - 2)
        } else {
            listState.scrollToItem(index)
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .background(OffWhite),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(
            items = daysInMonth,
            key = { it.dayOfMonth }
        ) { date ->
            DayItem(
                date = date,
                selected = (date == state.selectedDate),
                onClick = { onDaySelected(date) }
            )
        }
    }
}