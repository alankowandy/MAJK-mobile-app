package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ContainerState

@Composable
fun ContainerList(
    containers: List<ContainerState>,
    onSettingsClick: (Long) -> Unit,
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
            items = containers,
            key = { it.containerId }
        ) { container ->
            ContainerCard(
                containerNumber = container.containerNumber,
                medicamentName = container.medicamentName ?: "",
                containerState = container.containerState,
                numberOfPills = container.pillQuantity,
                onSettingsClick = { onSettingsClick(container.containerId) }
            )
        }
    }
}