package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.error_unknown
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.components.UserList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageFamilyScreenRoot(
    viewModel: ManageFamilyViewModel = koinViewModel(),
    onScheduleClick: (Long) -> Unit,
    onSettingsClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        println(lifecycleState.toString())
        if (lifecycleState == Lifecycle.State.STARTED && state.initialLoadDone) {
            viewModel.onAction(ManageFamilyAction.OnRefreshData)
        }
    }

    if (state.errorMessage != null) {
        MajkAlertDialog(
            error = state.errorMessage ?: stringResource(Res.string.error_unknown),
            dismissAction = { viewModel.onAction(ManageFamilyAction.OnDismissDialog) }
        )
    }

    ManageFamilyScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ManageFamilyAction.OnScheduleClick -> onScheduleClick(action.userId)
                is ManageFamilyAction.OnSettingsClick -> onSettingsClick(action.userId)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun ManageFamilyScreen(
    state: ManageFamilyState,
    onAction: (ManageFamilyAction) -> Unit
) {
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
            UserList(
                users = state.users,
                onScheduleClick = {
                    onAction(ManageFamilyAction.OnScheduleClick(it))
                },
                onSettingsClick = {
                    onAction(ManageFamilyAction.OnSettingsClick(it))
                }
            )
        }
    }
}