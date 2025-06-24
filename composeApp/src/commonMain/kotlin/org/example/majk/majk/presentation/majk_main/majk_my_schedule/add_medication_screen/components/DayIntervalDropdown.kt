package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.theme.Cyan
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayIntervalDropdown(
    value: String,
    isExpanded: Boolean,
    onAction: (AddScheduleAction) -> Unit
) {
    val dropdownOptions = (1..7).map { it.toString() }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { onAction(AddScheduleAction.OnDayIntervalClick) },
        modifier = Modifier
            .size(width = 80.dp, height = 55.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(isExpanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = DarkTeal,
                focusedBorderColor = DarkTeal,
                unfocusedTextColor = DarkTeal,
                focusedTextColor = DarkTeal,
                unfocusedContainerColor = Cyan,
                focusedContainerColor = Cyan,
                unfocusedTrailingIconColor = DarkTeal,
                focusedTrailingIconColor = DarkTeal
            ),
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onAction(AddScheduleAction.OnDayIntervalClick) },
            containerColor = Cyan
        ) {
            dropdownOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onAction(AddScheduleAction.OnTakingIntervalChange(option.toInt()))
                        onAction(AddScheduleAction.OnDayIntervalClick)
                    }
                )
            }
        }
    }
}