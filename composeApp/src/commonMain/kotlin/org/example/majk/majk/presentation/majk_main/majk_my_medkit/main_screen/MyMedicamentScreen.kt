package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MyMedicamentList
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.ContainerStateAction
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
        println(lifecycleState.toString())
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
            MajkAlertDialog(
                error = state.errorMessage,
                dismissAction = { onAction(MyMedicamentAction.OnDismissDialog) }
            )
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

                Spacer(modifier = Modifier.weight(1f))

                MajkButton(
                    text = "Dodaj lek",
                    onAction = { onAction(MyMedicamentAction.OnAddMedicamentClick) },
                    boldText = true,
                    modifier = Modifier
                        .padding(horizontal = 70.dp, vertical = 20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}