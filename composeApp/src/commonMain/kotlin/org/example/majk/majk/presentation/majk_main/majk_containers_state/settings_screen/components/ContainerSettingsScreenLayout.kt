package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
import majk.composeapp.generated.resources.container_state
import majk.composeapp.generated.resources.save
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.core.presentation.WatchYellow
import org.example.majk.majk.domain.ContainerSettings
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.components.MajkTextField
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsAction
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsState
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsAction
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerSettingsScreenLayout(
    state: ContainerSettingsState,
    searchQuery: String,
    searchResult: List<ContainerSettingsSearchQuery>,
    onAction: (ContainerSettingsAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val pillQuantityFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onAction(ContainerSettingsAction.OnSearchExpandedChange(false))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dokonujesz zmiany dla \nPojemnika ${state.containerSettings.containerNumber}",
            color = OffWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(DarkTeal, RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = OffWhite
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Zastąp lekarstwo",
                    color = DarkTeal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                CompositionLocalProvider(
                    LocalTextSelectionColors provides TextSelectionColors(
                        handleColor = DarkTeal,
                        backgroundColor = DarkTeal.copy(alpha = 0.33F)
                    )
                ) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = SearchBarDefaults.colors(
                            containerColor = LightGray
                        ),
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = searchQuery,
                                onQueryChange = { onAction(ContainerSettingsAction.OnSearchQueryChange(
                                    medicamentSearch = it,
                                    medicamentId = -1
                                )) },
                                expanded = state.isSearchExpanded,
                                onSearch = {},
                                onExpandedChange = { onAction(ContainerSettingsAction.OnSearchExpandedChange(it)) },
                                placeholder = { Text(text = "Wyszukaj lek") },
                                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                                trailingIcon = {
                                    if (searchQuery.isNotBlank()) {
                                        IconButton(
                                            onClick = {
                                                onAction(ContainerSettingsAction.OnSearchQueryChange(
                                                    medicamentSearch = "",
                                                    medicamentId = -1
                                                ))
                                            }
                                        ) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                        }
                                    }
                                }
                            )
                        },
                        expanded = state.isSearchExpanded,
                        onExpandedChange = { onAction(ContainerSettingsAction.OnSearchExpandedChange(it)) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            if (state.isSearching) {
                                CircularProgressIndicator(
                                    color = DarkTeal,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            } else {
                                SearchQueryResultList(
                                    searchResult = searchResult,
                                    onAction = onAction
                                )
                            }
                        }
                    }
                }

                Row {
                    Column {
                        Text(
                            text = "Liczba tabletek",
                            color = DarkTeal,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )

                        Text(
                            text = state.pillQuantity,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.container_state),
                            color = DarkTeal,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )

                        Text(
                            text = state.containerSettings.containerState,
                            fontSize = 16.sp,
                            color = OffWhite,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .background(
                                    color = when (state.containerSettings.containerState) {
                                        "dużo" -> GoGreen
                                        "mało" -> WatchYellow
                                        else -> WarningRed
                                    },
                                    shape = RoundedCornerShape(9.dp)
                                )
                                .padding(horizontal = 30.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "Dodaj liczbę tabletek",
                    color = DarkTeal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    IconButton(
                        onClick = {
                            onAction(ContainerSettingsAction.OnSubtractPillsClick)
                        },
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .border(1.dp, DarkTeal, RoundedCornerShape(100))
                            .background(LightGray, RoundedCornerShape(100)),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = DarkTeal
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "subtract"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    PillQuantityField(
                        state = state,
                        onAction = onAction,
                        focusRequester = pillQuantityFocusRequester,
                        onNextFocus = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                        isError = false
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            onAction(ContainerSettingsAction.OnAddPillsClick)
                        },
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .border(1.dp, DarkTeal, RoundedCornerShape(100))
                            .background(LightGray, RoundedCornerShape(100)),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = DarkTeal
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add"
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = stringResource(Res.string.save),
            onAction = {
                onAction(ContainerSettingsAction.OnConfirmClick)
                onAction(ContainerSettingsAction.OnBackClick)
            },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )

        MajkButton(
            text = "Opróżnij pojemnik",
            onAction = { onAction(ContainerSettingsAction.OnEmptyContainerClick) },
            boldText = true,
            containerColor = WarningRed,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(ContainerSettingsAction.OnBackClick) },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )
    }
}