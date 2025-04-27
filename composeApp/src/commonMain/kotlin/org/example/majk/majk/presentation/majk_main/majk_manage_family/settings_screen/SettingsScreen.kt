package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.majk.core.presentation.OffWhite
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    SettingsScreen()
}

@Composable
fun SettingsScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
    ) {

    }
}