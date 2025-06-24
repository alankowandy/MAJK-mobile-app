package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.components.AdminAuthUserList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminAuthScreenRoot(
    viewModel: AdminAuthViewModel = koinViewModel(),
    onEditClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AdminAuthScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AdminAuthAction.OnEditClick -> {
                    onEditClick(action.accountId)
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
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
                state = state,
                users = state.users,
                onAction = onAction
            )
        }
    }
}