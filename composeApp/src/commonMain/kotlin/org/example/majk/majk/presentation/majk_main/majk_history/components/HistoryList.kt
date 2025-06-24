package org.example.majk.majk.presentation.majk_main.majk_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryAction
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryState

@Composable
fun HistoryList(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = state.releaseHistory,
            key = { it.releaseHistoryId }
        ) { entry ->
            HistoryTile(
                entry = entry
            )
        }
    }
}