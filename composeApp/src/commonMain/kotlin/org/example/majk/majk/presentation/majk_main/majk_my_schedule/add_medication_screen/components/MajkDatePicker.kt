package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajkDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    initialDate: LocalDate?
) {
    val initialSelectedDate = initialDate?.toEpochMilliseconds()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDate
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(
                    text = "Ok",
                    color = DarkTeal
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = "Anuluj",
                    color = DarkTeal
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = DatePickerDefaults.colors(
            containerColor = OffWhite
        )
    ) {
        androidx.compose.material3.DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = OffWhite,
                titleContentColor = DarkTeal,
                headlineContentColor = DarkTeal,
                weekdayContentColor = DarkTeal,
                subheadContentColor = DarkTeal,
                navigationContentColor = DarkTeal,
                yearContentColor = DarkTeal,
                disabledSelectedYearContentColor = DarkTeal,
                currentYearContentColor = DarkTeal,
                selectedYearContentColor = OffWhite,
                disabledYearContentColor = DarkTeal,
                selectedYearContainerColor = DarkTeal,
                disabledSelectedYearContainerColor = OffWhite,
                dayContentColor = DarkTeal,
                disabledDayContentColor = DarkTeal,
                selectedDayContentColor = OffWhite,
                disabledSelectedDayContentColor = DarkTeal,
                selectedDayContainerColor = DarkTeal,
                disabledSelectedDayContainerColor = OffWhite,
                todayContentColor = DarkTeal,
                todayDateBorderColor = DarkTeal
            )
        )
    }
}

fun LocalDate.toEpochMilliseconds(): Long {
    return this
        .atStartOfDayIn(TimeZone.UTC)
        .toEpochMilliseconds()
}