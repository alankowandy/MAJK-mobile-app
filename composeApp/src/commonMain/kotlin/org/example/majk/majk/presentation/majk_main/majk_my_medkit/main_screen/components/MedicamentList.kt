package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.prescriptions
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.presentation.components.MajkButton
import org.example.majk.majk.domain.MyMedicamentList
import org.example.majk.majk.presentation.majk_main.components.ActionIcon
import org.example.majk.majk.presentation.majk_main.components.SwipeableIconWithActions
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentAction
import org.jetbrains.compose.resources.painterResource

@Composable
fun MedicamentList(
    medicamentList: List<MyMedicamentList>,
    onAction: (MyMedicamentAction) -> Unit,
    scrollState: LazyListState = rememberLazyListState()
) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        state = scrollState,
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
                            onAction(MyMedicamentAction.OnShowConfirmDialog(
                                medicamentId = medicament.medicamentId,
                                medicamentName = medicament.medicamentName
                            ))
                        },
                        backgroundColor = Color.Red,
                        icon = Icons.Outlined.Delete,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            ) {
                Spacer(modifier = Modifier.height(8.dp))

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
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(6.5f)
                        ) {
                            Text(
                                text = medicament.medicamentName,
                                color = DarkTeal,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .background(OffWhite)
                                    .weight(1f)
                            )

                            Text(
                                text = "Typ: ${medicament.medicamentType}",
                                color = DarkTeal,
                                fontWeight = FontWeight.Normal,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .background(OffWhite)
                                    .weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            modifier = Modifier
                                .weight(1f)
                                .size(40.dp)
                                .clip(RoundedCornerShape(100)),
                            onClick = {
                                medicament.leafletUrl.let { url ->
                                    uriHandler.openUri(url)
                                }
                            },
                            colors = IconButtonColors(
                                contentColor = OffWhite,
                                containerColor = DarkTeal,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = OffWhite
                            )
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.prescriptions),
                                contentDescription = "Medicament leaflet",
                                colorFilter = ColorFilter.tint(
                                    color = OffWhite
                                )
                            )
                        }
                    }
                }
            }
        }

        item {
            MajkButton(
                text = "Dodaj lek",
                onAction = { onAction(MyMedicamentAction.OnAddMedicamentClick) },
                boldText = true,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}