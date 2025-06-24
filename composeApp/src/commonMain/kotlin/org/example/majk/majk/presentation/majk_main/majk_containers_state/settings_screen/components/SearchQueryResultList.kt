package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsAction

@Composable
fun SearchQueryResultList(
    searchResult: List<ContainerSettingsSearchQuery>,
    onAction: (ContainerSettingsAction) -> Unit
) {
    LazyColumn {
        items(
            items = searchResult,
            key = { it.medicamentId }
        ) { medicament ->
            TextButton(
                onClick = {
                    onAction(ContainerSettingsAction.OnSearchQueryChange(
                        medicamentSearch = medicament.medicamentName,
                        medicamentId = medicament.medicamentId
                    ))
                    onAction(ContainerSettingsAction.OnSearchExpandedChange(false))
                }
            ) {
                Text(
                    text = medicament.medicamentName,
                    color = DarkTeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}