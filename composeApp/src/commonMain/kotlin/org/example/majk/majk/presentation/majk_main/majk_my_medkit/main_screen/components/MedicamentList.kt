package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MyMedicamentList
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentAction
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentState
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MedicamentList(
    medicamentList: List<MyMedicamentList>,
    onAction: (MyMedicamentAction) -> Unit
) {
    val uriHandler = LocalUriHandler.current

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
                        onClick = {
                            //medicamentList[index] = medicament.copy(isOptionsRevealed = false)
                            onAction(
                                MyMedicamentAction.OnDeleteMedicamentClick(
                                medicamentId = medicament.medicamentId
                            ))
                            onAction(MyMedicamentAction.OnRefreshData)
                        },
                        backgroundColor = Color.Red,
                        icon = Icons.Outlined.Delete,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(OffWhite)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = OffWhite)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = medicament.medicamentName,
                            color = DarkTeal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .background(OffWhite)
                                .weight(1f)
                        )

                        IconButton(
                            onClick = {
                                medicament.leafletUrl.let { url ->
                                    uriHandler.openUri(url)
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = DarkTeal
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = "leaflet"
                            )
                        }
                    }
                }
            }
        }
    }
}