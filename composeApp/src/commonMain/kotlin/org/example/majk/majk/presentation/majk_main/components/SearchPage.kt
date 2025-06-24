package org.example.majk.majk.presentation.majk_main.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.LightGray
import org.example.majk.core.presentation.theme.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    onTrailingIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
    ) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = DarkTeal,
                backgroundColor = DarkTeal.copy(alpha = 0.33F)
            )
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery,
                        onQueryChange = { onQueryChange(it) },
                        expanded = false,
                        onSearch = {},
                        onExpandedChange = {},
                        enabled = true,
                        placeholder = { Text(text = placeholder) },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(
                                    onClick = { onTrailingIconClick() }
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                }
                            }
                        }
                    )
                },
                expanded = false,
                modifier = modifier,
                shape = RoundedCornerShape(24.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                onExpandedChange = {},
                content = {},
            )
        }


    }
}

@Composable
private fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}

@Composable
private fun <T> CategoryItem(
    item: T,
    text: String,
    modifier: Modifier = Modifier
) {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <T, K : Comparable<K>> GroupedLazyColumn(
    items: List<T>,
    keySelector: (T) -> K,
    headerContent: @Composable (K) -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    val groupedMap: Map<K, List<T>> = items.groupBy(keySelector)
    val sortedKeys: List<K> = groupedMap.keys.sorted()

    LazyColumn {
        sortedKeys.forEach { key ->
            stickyHeader {
                headerContent(key)
            }

            items(groupedMap.getValue(key)) { item ->
                itemContent(item)
            }
        }

    }
}