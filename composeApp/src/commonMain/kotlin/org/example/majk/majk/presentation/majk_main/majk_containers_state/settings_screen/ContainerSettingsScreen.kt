package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ContainerSettings
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.components.ContainerSettingsScreenLayout

@Composable
fun ContainerSettingsScreenRoot(
    viewModel: ContainerSettingsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    ContainerSettingsScreen(
        state = state,
        searchQuery = searchQuery,
        searchResult = searchResult,
        onAction = { action ->
            when (action) {
                is ContainerSettingsAction.OnBackClick -> {
                    onBackClick()
                }
                else -> { viewModel.onAction(action) }
            }
        }
    )
}

@Composable
fun ContainerSettingsScreen(
    state: ContainerSettingsState,
    searchQuery: String,
    searchResult: List<ContainerSettingsSearchQuery>,
    onAction: (ContainerSettingsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal
            )
        } else {
            ContainerSettingsScreenLayout(
                state = state,
                searchQuery = searchQuery,
                searchResult = searchResult,
                onAction = onAction
            )
        }
    }
}