package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ContainerSettingsScreenRoot(
    viewModel: ContainerSettingsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

}

@Composable
fun ContainerSettingsScreen(
    state: ContainerSettingsState,
    onAction: (ContainerSettingsAction) -> Unit
) {

}