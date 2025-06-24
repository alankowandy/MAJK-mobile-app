package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings.components.AdminAuthSettingsLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminAuthSettingsScreenRoot(
    viewModel: AdminAuthSettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AdminAuthSettingsScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AdminAuthSettingsScreen(
    state: AdminAuthSettingsState,
    onAction: (AdminAuthSettingsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal
            )
        } else {
            AdminAuthSettingsLayout(
                state = state,
                onAction = onAction
            )
        }
    }
}