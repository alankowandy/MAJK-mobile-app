package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import org.example.majk.core.presentation.theme.LightGray
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditAction
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchQueryResultList(
    searchResults: List<MedicamentSearch>,
    onAction: (MyMedkitEditAction) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(

    ) {
        items(
            items = searchResults,
            key = { it.medicamentId }
        ) { medicament ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .background(color = LightGray.copy(alpha = 0.5f))
                        .clickable {
                            onAction(MyMedkitEditAction.OnSearchQueryChange(
                                medicamentSearch = medicament.medicamentName
                            ))
                            onAction(MyMedkitEditAction.OnSaveClick(
                                medicamentId = medicament.medicamentId
                            ))
                            onAction(MyMedkitEditAction.OnBackClick)
                        }
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(4.25f)
                    ) {
                        Text(
                            text = medicament.medicamentName,
                            color = DarkTeal,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = medicament.medicamentType,
                            fontWeight = FontWeight.Normal,
                            color = DarkTeal,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                            .size(40.dp)
                            .clip(RoundedCornerShape(100)),
                        onClick = {
                            medicament.medicamentLeaflet.let { url ->
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

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = DarkTeal.copy(alpha = 0.5f)
                )
            }
//            TextButton(
//                onClick = {
//                    onAction(MyMedkitEditAction.OnSearchQueryChange(
//                        medicamentSearch = medicament.medicamentName,
//                        medicamentId = medicament.medicamentId,
//                        medicamentLeaflet = medicament.medicamentLeaflet
//                    ))
//                    onAction(MyMedkitEditAction.OnSearchExpandedChange(false))
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentWidth(Alignment.Start)
//            ) {
//                Column(
//                    modifier = Modifier
//                ) {
//                    Text(
//                        text = medicament.medicamentName,
//                        color = DarkTeal,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp
//                    )
//
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    Text(
//                        text = medicament.medicamentType,
//                        fontWeight = FontWeight.Normal,
//                        color = DarkTeal,
//                        fontSize = 12.sp
//                    )
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                IconButton(
//                    onClick = {
//                        medicament.medicamentLeaflet.let { url ->
//                            uriHandler.openUri(url)
//                        }
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Description,
//                        contentDescription = "Medicament leaflet"
//                    )
//                }
        }
    }
}