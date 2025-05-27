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
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsState

@Composable
fun HourlySchedule(
    state: DetailsState,
    releaseSchedule: List<ReleaseSchedule>,
    onRefreshTime: () -> Unit
) {

    val listState = rememberLazyListState()

    // Side-effect to auto-scroll to current hour on first composition
    LaunchedEffect(state.selectedDate, state.currentHour) {
        if (state.currentDay == state.selectedDate) {
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
                state = state,
                hour = hour,
                releaseSchedule = releaseSchedule,
                showCurrentTimeLine = (state.currentDay == state.selectedDate && hour == state.currentHour)
            )
        }
    }
}