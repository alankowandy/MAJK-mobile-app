package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.theme.Cyan
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.LightGray
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditAction
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditState

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
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = LightGray
                ),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery,
                        onQueryChange = { onAction(MyMedkitEditAction.OnSearchQueryChange(
                            medicamentSearch = it
                        )) },
                        expanded = false,
                        onSearch = {},
                        onExpandedChange = { },
                        placeholder = { Text(text = "Wyszukaj lek") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        onAction(MyMedkitEditAction.OnSearchQueryChange(
                                            medicamentSearch = ""
                                        ))
                                    }
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = DarkTeal,
                            focusedTextColor = DarkTeal,
                            unfocusedTextColor = DarkTeal,
                            focusedPlaceholderColor = DarkTeal,
                            unfocusedPlaceholderColor = DarkTeal
                        )
                    )
                },
                expanded = false,
                onExpandedChange = { }
            ) { }
        }

//        HorizontalDivider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp),
//            thickness = 4.dp,
//            color = Cyan
//        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 2.dp,
                    color = Cyan,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(color = LightGray.copy(alpha = 0.5f))
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

//        MajkButton(
//            text = stringResource(Res.string.save),
//            onAction = {
//                onAction(MyMedkitEditAction.OnSaveClick)
//                onAction(MyMedkitEditAction.OnBackClick)
//            },
//            boldText = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 70.dp)
//        )
//
//        MajkButton(
//            text = stringResource(Res.string.back),
//            onAction = { onAction(MyMedkitEditAction.OnBackClick) },
//            boldText = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 70.dp, vertical = 5.dp)
//        )
    }
}

