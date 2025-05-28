package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleState
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddScheduleScreenLayout(
    state: AddScheduleState,
    searchQuery: String,
    onAction: (AddScheduleAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
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

        ConsumptionBox(
            beforeMeal = state.beforeMeal,
            duringMeal = state.duringMeal,
            afterMeal = state.afterMeal,
            onAction = onAction
        )

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = "Zapisz",
            onAction = {
                onAction(AddScheduleAction.OnSaveClick)
                onAction(AddScheduleAction.OnBackClick)
            },
            boldText = true,
            containerColor = DarkTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(AddScheduleAction.OnBackClick) },
            boldText = true,
            containerColor = DarkTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        )
    }
}