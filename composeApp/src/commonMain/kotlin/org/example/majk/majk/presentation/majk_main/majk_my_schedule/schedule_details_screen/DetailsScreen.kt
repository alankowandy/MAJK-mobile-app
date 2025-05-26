package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components.DayPicker
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components.HourlySchedule

@Composable
fun DetailsScreenRoot(
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val releaseSchedule by viewModel.releaseSchedule.collectAsStateWithLifecycle()

    DetailsScreen(
        state = state,
        releaseSchedule = releaseSchedule,
        onAction = { action ->
            when (action) {
                is DetailsAction.OnBackClick -> {
                    onBackClick()
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}


@Composable
fun DetailsScreen(
    state: DetailsState,
    releaseSchedule: List<ReleaseSchedule>,
    onAction: (DetailsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DayPicker(
                state = state,
                onDaySelected = { date ->
                    onAction(DetailsAction.OnSelectDate(date))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            HourlySchedule(
                state = state,
                releaseSchedule = releaseSchedule,
                onRefreshTime = {
                    onAction(DetailsAction.OnRefreshCurrentTime)
                }
            )

        }


    }
}