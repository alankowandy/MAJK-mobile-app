package org.example.majk.majk.presentation.majk_main.majk_admin_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.AdminAuthUsers
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.components.AdminAuthUserList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminAuthScreenRoot(
    viewModel: AdminAuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AdminAuthScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AdminAuthScreen(
    state: AdminAuthState,
    onAction: (AdminAuthAction) -> Unit
) {
    if (state.errorMessage != null) {
        MajkAlertDialog(
            title = ":(",
            error = state.errorMessage,
            dismissAction = { onAction(AdminAuthAction.OnDismissError) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal
            )
        } else if (state.users.isEmpty()) {
            EmptyListText()
        } else {
            AdminAuthUserList(
                users = state.users,
                onAction = onAction
            )
        }
    }
}