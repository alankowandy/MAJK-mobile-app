package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction

@Composable
fun ConsumptionBox(
    beforeMeal: Boolean,
    duringMeal: Boolean,
    afterMeal: Boolean,
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Spożycie:",
                color = DarkTeal
            )

            ConsumptionCheckbox(
                label = "Przed posiłkiem",
                isChecked = beforeMeal,
                onCheckedChange = { onAction(AddScheduleAction.OnToggleBeforeMeal) }
            )
            ConsumptionCheckbox(
                label = "W trakcie posiłku",
                isChecked = duringMeal,
                onCheckedChange = { onAction(AddScheduleAction.OnToggleDuringMeal) }
            )
            ConsumptionCheckbox(
                label = "Po posiłku",
                isChecked = afterMeal,
                onCheckedChange = { onAction(AddScheduleAction.OnToggleAfterMeal) }
            )
        }
    }
}