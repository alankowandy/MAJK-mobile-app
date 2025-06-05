package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
import majk.composeapp.generated.resources.save
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.core.presentation.components.MajkButton
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditAction
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMedkitEditLayout(
    state: MyMedkitEditState,
    searchQuery: String,
    searchResult: List<MedicamentSearch>,
    onAction: (MyMedkitEditAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onAction(MyMedkitEditAction.OnSearchExpandedChange(false))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = DarkTeal,
                backgroundColor = DarkTeal.copy(alpha = 0.33F)
            )
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(16.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = LightGray
                ),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery,
                        onQueryChange = { onAction(MyMedkitEditAction.OnSearchQueryChange(
                            medicamentSearch = it,
                            medicamentId = -1,
                            medicamentLeaflet = ""
                        )) },
                        expanded = state.isSearchExpanded,
                        onSearch = {},
                        onExpandedChange = { onAction(MyMedkitEditAction.OnSearchExpandedChange(it)) },
                        placeholder = { Text(text = "Wyszukaj lek") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        onAction(MyMedkitEditAction.OnSearchQueryChange(
                                            medicamentSearch = "",
                                            medicamentId = -1,
                                            medicamentLeaflet = ""
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
                onExpandedChange = { onAction(MyMedkitEditAction.OnSearchExpandedChange(it)) }
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
                            searchResults = searchResult,
                            onAction = onAction
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = stringResource(Res.string.save),
            onAction = {
                onAction(MyMedkitEditAction.OnSaveClick)
                onAction(MyMedkitEditAction.OnBackClick)
            },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(MyMedkitEditAction.OnBackClick) },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp, vertical = 5.dp)
        )
    }
}