package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction

@Composable
fun SearchQueryResult(
    searchResults: List<ContainerSettingsSearchQuery>,
    onAction: (AddScheduleAction) -> Unit
) {
    LazyColumn {
        items(
            items = searchResults,
            key = { it.medicamentId }
        ) { medicament ->
            TextButton(
                onClick = {
                    onAction(AddScheduleAction.OnSearchQueryChange(
                        medicamentSearch = medicament.medicamentName,
                        medicamentId = medicament.medicamentId
                    ))
                    onAction(AddScheduleAction.OnSearchExpandedChange(false))
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = medicament.medicamentName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    color = DarkTeal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
            }
        }
    }
}