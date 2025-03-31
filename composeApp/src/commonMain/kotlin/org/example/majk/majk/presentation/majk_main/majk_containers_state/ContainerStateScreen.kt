package org.example.majk.majk.presentation.majk_main.majk_containers_state

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.example.majk.majk.presentation.majk_main.majk_containers_state.components.ContainerCard

@Composable
fun ContainerStateScreenRoot() {
    Column {
        ContainerCard(
            containerNumber = 1,
            medicamentName = "kofeina 200mg",
            state = "mało",
            numberOfPills = 120,
            onFillContainerClick = {},
            onEmptyContainerClick = {},
            onReplaceMedicamentClick = {},
            onInformationClick = {}
        )
    }
}