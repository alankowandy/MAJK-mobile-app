package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.presentation.majk_main.components.ActionIcon
import org.example.majk.majk.presentation.majk_main.components.SwipeableIconWithActions
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.SaveStatus
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.ScheduledMedicineListAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.ScheduledMedicineListState

@Composable
fun MedicineList(
    state: ScheduledMedicineListState,
    onAction: (ScheduledMedicineListAction) -> Unit,
    medicineList: List<MedicineEntry>,
    scrollState: LazyListState = rememberLazyListState()
) {
    val uriHandler = LocalUriHandler.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
    ) {
        itemsIndexed(
            items = medicineList
        ) { _, medicament ->
            Column {
                SwipeableIconWithActions(
                    isRevealed = medicament.isOptionsRevealed,
                    actions = {
                        ActionIcon(
                            onClick = {
                                onAction(ScheduledMedicineListAction.OnNoteDetailsClick(medicament))
                            },
                            backgroundColor = Cyan,
                            icon = Icons.Default.MoreVert,
                            modifier = Modifier.fillMaxHeight()
                        )
                        ActionIcon(
                            onClick = {
                                //medicamentList[index] = medicament.copy(isOptionsRevealed = false)
                                onAction(ScheduledMedicineListAction.OnDeleteClick(medicament.releaseId))
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
                                    onAction(ScheduledMedicineListAction.OnToggleNoteEditor(medicament.releaseId))
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = DarkTeal
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                                    contentDescription = "leaflet"
                                )
                            }

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

                if (medicament.isTextEditorVisible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        CompositionLocalProvider(
                            LocalTextSelectionColors provides TextSelectionColors(
                                handleColor = DarkTeal,
                                backgroundColor = DarkTeal.copy(alpha = 0.33F)
                            )
                        ) {
                            TextField(
                                value = medicament.note ?: "",
                                onValueChange = { newText ->
                                    onAction(ScheduledMedicineListAction.OnNoteChange(
                                        releaseId = medicament.releaseId,
                                        note = newText
                                    ))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        elevation = 6.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                placeholder = { Text("Twoja notatka…") },
                                minLines = 5,
                                shape = RoundedCornerShape(16.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = OffWhite,
                                    unfocusedContainerColor = OffWhite,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = DarkTeal,
                                    focusedTextColor = DarkTeal,
                                    unfocusedTextColor = DarkTeal,
                                    unfocusedPlaceholderColor = DarkTeal,
                                    focusedPlaceholderColor = DarkTeal,
                                    cursorColor = DarkTeal
                                )
                            )
                        }

                        val saveStatus = state.saveStatuses[medicament.releaseId] ?: SaveStatus.Idle

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                        ) {
                            SaveButton(
                                saveStatus = saveStatus,
                                onSave = {
                                    onAction(ScheduledMedicineListAction.OnSaveNoteClick(
                                        releaseId = medicament.releaseId,
                                        note = medicament.note ?: ""
                                    ))
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun SaveButton(
    saveStatus: SaveStatus,
    onSave: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = saveStatus) { status ->
            when (status) {
                SaveStatus.Idle -> {
                    IconButton(onClick = { onSave() }) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "save",
                            tint = DarkTeal
                        )
                    }
                }
                SaveStatus.Saving -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        strokeWidth = 2.dp,
                        color = DarkTeal
                    )
                }
                SaveStatus.Success -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "saved",
                        tint = GoGreen,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}