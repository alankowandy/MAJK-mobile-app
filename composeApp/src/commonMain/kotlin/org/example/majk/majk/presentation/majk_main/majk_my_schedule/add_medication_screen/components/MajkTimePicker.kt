package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajkTimePicker(
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    time: LocalTime?
) {
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw"))

    val timePickerState = rememberTimePickerState(
        initialHour = time?.hour ?: currentTime.hour,
        initialMinute = time?.minute ?: currentTime.minute,
        is24Hour = true
    )

    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = OffWhite,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                TimePicker(
                    state = timePickerState,
                    layoutType = TimePickerDefaults.layoutType(),
                    colors = TimePickerDefaults.colors(
                        clockDialColor = OffWhite,
                        clockDialSelectedContentColor = DarkTeal,
                        clockDialUnselectedContentColor = DarkTeal,

                        selectorColor = Cyan,

                        containerColor = OffWhite,

                        timeSelectorSelectedContainerColor = Cyan,
                        timeSelectorUnselectedContainerColor = OffWhite,
                        timeSelectorSelectedContentColor = OffWhite,
                        timeSelectorUnselectedContentColor = DarkTeal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.textButtonColors(contentColor = DarkTeal)
                    ) {
                        Text(text = "Anuluj")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onConfirm(
                                LocalTime(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                            )
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = DarkTeal)
                    ) {
                        Text(text = "Ok")
                    }
                }
            }

        }

    }
}