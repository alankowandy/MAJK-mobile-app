package org.example.majk.majk.presentation.majk_main.majk_my_medkit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MyMedicamentList
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentAction

@Composable
fun MedicamentList(
    medicamentList: List<MyMedicamentList>,
    onAction: (MyMedicamentAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(OffWhite)
    ) {
        itemsIndexed(
            items = medicamentList,
            key = { _, medicament -> medicament.medicamentId }
        ) { index, medicament ->
            SwipeableIconWithActions(
                isRevealed = medicament.isOptionsRevealed,
                actions = {
                    ActionIcon(
                        onClick = { onAction(MyMedicamentAction.OnMedicamentDetailsClick) },
                        backgroundColor = Cyan,
                        icon = Icons.Outlined.MoreVert,
                        modifier = Modifier.fillMaxHeight()
                    )
                    ActionIcon(
                        onClick = {
                            //medicamentList[index] = medicament.copy(isOptionsRevealed = false)
                            onAction(
                                MyMedicamentAction.OnDeleteMedicamentClick(
                                medicamentId = medicament.medicamentId
                            ))
                        },
                        backgroundColor = Color.Red,
                        icon = Icons.Outlined.Delete,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            ) {
                Text(
                    text = medicament.medicamentName,
                    color = DarkTeal,
                    modifier = Modifier
                        .background(OffWhite)
                        .padding(8.dp)
                )
            }
        }
    }
}