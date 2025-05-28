package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    state: AddScheduleState,
    searchQuery: String,
    onAction: (AddScheduleAction) -> Unit
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = DarkTeal,
            backgroundColor = DarkTeal.copy(alpha = 0.33F)
        )
    ) {
        androidx.compose.material3.SearchBar(
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
                    onQueryChange = {
                        onAction(AddScheduleAction.OnSearchQueryChange(
                            medicamentSearch = it,
                            medicamentId = -1
                        ))
                    },
                    expanded = state.isSearchExpanded,
                    onSearch = {},
                    onExpandedChange = { onAction(AddScheduleAction.OnSearchExpandedChange(it)) },
                    placeholder = { Text(text = "Wyszukaj lek") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    onAction(AddScheduleAction.OnSearchQueryChange(
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
            onExpandedChange = { onAction(AddScheduleAction.OnSearchExpandedChange(it)) }
        ) {
            if (state.isSearching) {
                CircularProgressIndicator(
                    color = DarkTeal,
                    modifier = Modifier
                        .heightIn(max = 500.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                SearchQueryResult(
                    searchResults = state.searchResult,
                    onAction = onAction
                )
            }
//            Box(
//                modifier = Modifier
//                    .height(500.dp)
//            ) {
//                if (state.isSearching) {
//                    CircularProgressIndicator(
//                        color = DarkTeal,
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                    )
//                } else {
//                    SearchQueryResult(
//                        searchResults = state.searchResult,
//                        onAction = onAction
//                    )
//                }
//            }
        }
    }
}