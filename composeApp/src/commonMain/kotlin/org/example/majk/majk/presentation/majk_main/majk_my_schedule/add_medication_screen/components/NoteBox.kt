package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction

@Composable
fun NoteBox(
    noteText: String,
    onAction: (AddScheduleAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = DarkTeal, shape = RoundedCornerShape(16.dp))
            .background(OffWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Notatka:",
                color = DarkTeal,
                modifier = Modifier
                    .padding(8.dp)
            )

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = DarkTeal,
                    backgroundColor = DarkTeal.copy(alpha = 0.33F)
                )
            ) {
                TextField(
                    value = noteText,
                    onValueChange = { onAction(AddScheduleAction.OnNoteChange(it)) },
                    minLines = 6,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedTextColor = DarkTeal,
                        unfocusedTextColor = DarkTeal,
                        cursorColor = DarkTeal
                    )
                )
            }
        }
    }
}