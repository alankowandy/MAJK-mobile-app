package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
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
    containerSettings: ContainerSettings,
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
            text = "Dokonujesz zmiany dla Pojemnika ${containerSettings.containerNumber}",
            color = DarkTeal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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

                Text(
                    text = "Liczba tabletek",
                    color = DarkTeal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

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
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = "Zapisz",
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