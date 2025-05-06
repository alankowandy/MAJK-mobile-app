package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

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
import org.example.majk.majk.domain.UserSettings
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.components.ManageFamilySettingsLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        userSettings = userSettings,
        onBackClick = onBackClick,
        onAction = { action ->
            when (action) {
                is SettingsAction.OnBackClick -> {
                    onBackClick()
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    userSettings: UserSettings,
    onBackClick: () -> Unit,
    onAction: (SettingsAction) -> Unit
) {
    if (state.errorMessage != null) {
        MajkAlertDialog(
            error = state.errorMessage,
            dismissAction = { onAction(SettingsAction.OnDismissClick) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(color = DarkTeal)
        } else {
            ManageFamilySettingsLayout(
                state = state,
                userSettings = userSettings,
                onBackClick = onBackClick,
                onAction = onAction
            )
        }
    }
}