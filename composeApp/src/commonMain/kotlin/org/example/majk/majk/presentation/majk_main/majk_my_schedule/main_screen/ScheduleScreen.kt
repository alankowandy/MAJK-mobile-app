package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDate
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.components.PolishMonthCalendar

@Composable
fun ScheduleScreenRoot(
    viewModel: ScheduleViewModel,
    onDateClick: (String) -> Unit,
    onMedicineListClick: (Long) -> Unit,
    onAddScheduleClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScheduleScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ScheduleAction.OnSelectDate -> {
                    onDateClick(action.date.toString())
                }
                is ScheduleAction.OnMedicineListClick -> {
                    onMedicineListClick(action.accountId)
                }
                is ScheduleAction.OnAddScheduleClick -> {
                    onAddScheduleClick(action.accountId)
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun ScheduleScreen(
    state: ScheduleState,
    onAction: (ScheduleAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .padding(8.dp)
    ) {
        PolishMonthCalendar(
            state = state,
            onMonthOffsetChange = { onAction(ScheduleAction.OnMonthOffsetChange(it)) },
            onDateSelected = {
                onAction(ScheduleAction.OnSelectDate(it))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = "Szczegóły",
            onAction = {
                onAction(ScheduleAction.OnSelectDate(state.selectedDate))
            },
            boldText = true,
            containerColor = Cyan,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .padding(bottom = 5.dp)
        )

        MajkButton(
            text = "Lista dawek",
            onAction = { onAction(ScheduleAction.OnMedicineListClick(state.currentAccountId)) },
            boldText = true,
            containerColor = DarkTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .padding(bottom = 5.dp)
        )

        MajkButton(
            text = "Dodaj dawkę",
            onAction = { onAction(ScheduleAction.OnAddScheduleClick(state.currentAccountId)) },
            boldText = true,
            containerColor = DarkTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .padding(bottom = 5.dp)
        )
    }
}