package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditAction

@Composable
fun SearchQueryResultList(
    searchResults: List<MedicamentSearch>,
    onAction: (MyMedkitEditAction) -> Unit
) {
    LazyColumn {
        items(
            items = searchResults,
            key = { it.medicamentId }
        ) { medicament ->
            TextButton(
                onClick = {
                    onAction(MyMedkitEditAction.OnSearchQueryChange(
                        medicamentSearch = medicament.medicamentName,
                        medicamentId = medicament.medicamentId,
                        medicamentLeaflet = medicament.medicamentLeaflet
                    ))
                    onAction(MyMedkitEditAction.OnSearchExpandedChange(false))
                }
            ) {
                Text(
                    text = medicament.medicamentName,
                    color = DarkTeal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}