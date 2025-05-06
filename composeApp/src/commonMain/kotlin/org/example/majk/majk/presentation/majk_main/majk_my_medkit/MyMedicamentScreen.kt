package org.example.majk.majk.presentation.majk_main.majk_my_medkit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import org.example.majk.majk.domain.MyMedicamentList
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.components.MedicamentList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyMedicamentScreenRoot(
    viewModel: MyMedicamentViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val medicamentList by viewModel.myMedicamentList.collectAsStateWithLifecycle()

    MyMedicamentScreen(
        state = state,
        medicamentList = medicamentList,
        onAction = { action ->
            when (action) {
                is MyMedicamentAction.OnAddMedicamentClick -> {}
                is MyMedicamentAction.OnMedicamentDetailsClick -> {}
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
    medicamentList: List<MyMedicamentList>,
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
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MedicamentList(
                    medicamentList = medicamentList,
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
                        .height(50.dp)
                )
            }
        }
    }
}