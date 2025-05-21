package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ContainerState
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileAction
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.components.ContainerCard
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.components.ContainerList
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyAction

@Composable
fun ContainerStateScreenRoot(
    viewModel: ContainerStateViewModel,
    onSettingsClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val containersList by viewModel.containersList.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        println(lifecycleState.toString())
        if (lifecycleState == Lifecycle.State.STARTED && state.initialLoadDone) {
            viewModel.onAction(ContainerStateAction.OnRefreshData)
        }
    }

    ContainerScreen(
        state = state,
        containersList = containersList,
        onAction = { action ->
            when (action) {
                is ContainerStateAction.OnSettingsClick -> {
                    onSettingsClick(action.containerId)
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun ContainerScreen(
    state: ContainerScreenState,
    containersList: List<ContainerState>,
    onAction: (ContainerStateAction) -> Unit
) {
    if (state.errorMessage != null) {
        MajkAlertDialog(
            error = state.errorMessage,
            dismissAction = {
                onAction(ContainerStateAction.OnDialogClear)
            }
        )
    }

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
            ContainerList(
                containers = containersList,
                onSettingsClick = { onAction(ContainerStateAction.OnSettingsClick(it)) }
            )
        }
    }
}