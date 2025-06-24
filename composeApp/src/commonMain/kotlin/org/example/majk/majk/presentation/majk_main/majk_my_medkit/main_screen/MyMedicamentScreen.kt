package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.my_medicament_confirm_text
import majk.composeapp.generated.resources.my_medicament_confirm_text2
import majk.composeapp.generated.resources.my_medicament_confirm_title
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.ConfirmDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.components.MedicamentList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyMedicamentScreenRoot(
    viewModel: MyMedicamentViewModel = koinViewModel(),
    onAddMedicamentClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.STARTED && state.initialLoadDone) {
            viewModel.onAction(MyMedicamentAction.OnRefreshData)
        }
    }

    MyMedicamentScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is MyMedicamentAction.OnAddMedicamentClick -> {
                    onAddMedicamentClick()
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun MyMedicamentScreen(
    state: MyMedicamentState,
    onAction: (MyMedicamentAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite),
        contentAlignment = Alignment.Center
    ) {
        if (state.errorMessage != null) {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    // start the slide from full height (off-screen below)
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(300)),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            ) {
                MajkAlertDialog(
                    error = state.errorMessage,
                    dismissAction = { onAction(MyMedicamentAction.OnDismissErrorDialog) }
                )
            }

//            MajkAlertDialog(
//                error = state.errorMessage,
//                dismissAction = { onAction(MyMedicamentAction.OnDismissErrorDialog) }
//            )
        }

        if (state.isConfirmDialogVisible) {
            AnimatedVisibility(
                visible = state.isConfirmDialogVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(200, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(200))
            ) {
                ConfirmDialog(
                    title = stringResource(Res.string.my_medicament_confirm_title),
                    text = buildString {
                        append(stringResource(Res.string.my_medicament_confirm_text))
                        append(state.deleteMedicamentName)
                        append(stringResource(Res.string.my_medicament_confirm_text2))
                    },
                    onConfirm = {
                        onAction(MyMedicamentAction.OnDeleteMedicamentClick(state.deleteMedicamentId))
                        onAction(MyMedicamentAction.OnRefreshData)
                        onAction(MyMedicamentAction.OnDismissConfirmDialog)
                    },
                    onDismissDialog = { onAction(MyMedicamentAction.OnDismissConfirmDialog) }
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal
            )
        } else if (state.myMedicamentList.isEmpty()) {
            EmptyListText()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MedicamentList(
                    medicamentList = state.myMedicamentList,
                    onAction = onAction
                )
            }
        }
    }
}