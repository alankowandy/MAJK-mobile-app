package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsState

@Composable
fun HourlySchedule(
    state: DetailsState,
    schedule: Map<Int, List<MedicineEntry>>,
    onRefreshTime: () -> Unit
) {
    // Determine if the selected date (from currentDateTime) is today:

    val currentHour = state.currentDateTime.hour
    val currentMinute = state.currentDateTime.minute

    // Remember a LazyListState to control scrolling
    val listState = rememberLazyListState()

    // Side-effect to auto-scroll to current hour on first composition
    LaunchedEffect(Unit) {
        if (state.currentDay == state.selectedDate) {
            // Scroll so that the current hour item is near top (index = currentHour)
            listState.animateScrollToItem(index = state.currentHour)
        }
    }

    if (state.selectedDate == state.currentDay) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(60_000L)
                onRefreshTime()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        state = listState,
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(24) { hour ->  // 0 through 23
            HourSlot(
                hour = hour,
                medicines = schedule[hour].orEmpty(),
                showCurrentTimeLine = (state.currentDay == state.selectedDate && hour == state.currentHour),
                currentMinute = state.currentMinute
            )
        }
    }
}