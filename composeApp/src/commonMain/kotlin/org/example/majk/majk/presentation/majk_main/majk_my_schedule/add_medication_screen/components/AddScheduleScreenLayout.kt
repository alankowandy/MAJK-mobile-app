package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleState

@Composable
fun AddScheduleScreenLayout(
    state: AddScheduleState,
    searchQuery: String,
    onAction: (AddScheduleAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onAction(AddScheduleAction.OnSearchExpandedChange(false))
                }
            },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            state = state,
            onAction = onAction,
            searchQuery = searchQuery
        )

        AddScheduleBox(
            text = "Data początkowa:",
            onClick = {
                onAction(AddScheduleAction.OnStartDateClick)
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = if (state.startDateValue == null) "" else state.startDateValue.toString(),
                color = DarkTeal,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 2.dp, end = 8.dp)
            )
        }

        if (state.isStartDateVisible) {
            MajkDatePicker(
                onDateSelected = {
                    onAction(AddScheduleAction.OnStartDatePick(it))
                },
                onDismiss = {
                    onAction(AddScheduleAction.OnStartDateClick)
                },
                initialDate = state.startDateValue
            )
        }

        AddScheduleBox(
            text = "Data końcowa:",
            onClick = {
                onAction(AddScheduleAction.OnEndDateClick)
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = if (state.endDateValue == null) "" else state.endDateValue.toString(),
                color = DarkTeal,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 2.dp, end = 8.dp)
            )
        }

        if (state.isEndDateVisible) {
            MajkDatePicker(
                onDateSelected = {
                    onAction(AddScheduleAction.OnEndDatePick(it))
                },
                onDismiss = {
                    onAction(AddScheduleAction.OnEndDateClick)
                },
                initialDate = state.endDateValue
            )
        }

        AddScheduleBox(
            text = "Godzina przyjęcia:",
            modifier = Modifier
                .padding(horizontal = 24.dp),
            onClick = { onAction(AddScheduleAction.OnTimePickClick) }
        ) {
            SelectedTimeBox(
                hour = state.time?.hour ?: Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).hour,
                minute = state.time?.minute ?: Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).minute,
                onClick = { onAction(AddScheduleAction.OnTimePickClick) }
            )
        }

        if (state.isTimePickerVisible) {
            MajkTimePicker(
                onDismiss = { onAction(AddScheduleAction.OnTimePickClick) },
                onConfirm = {
                    onAction(AddScheduleAction.OnTakingTimePick(it))
                    onAction(AddScheduleAction.OnTimePickClick)
                },
                time = state.time
            )
        }

        AddScheduleBox(
            text = "Co ile dni:",
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            DayIntervalDropdown(
                value = state.intervalDays.toString(),
                isExpanded = state.isIntervalDropdownVisible,
                onAction = onAction
            )
        }

        AddScheduleBox(
            text = "Ilość:",
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            PillAmountDropdown(
                value = state.pillAmount.toString(),
                isExpanded = state.isPillAmountDropdownVisible,
                onAction = onAction
            )
        }

        AddScheduleBox(
            text = "Notatka:",
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {

        }
    }
}