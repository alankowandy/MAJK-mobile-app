package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen

import org.example.majk.majk.domain.ContainerState

data class ContainerScreenState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val initialLoadDone: Boolean = false,
    val containersList: List<ContainerState> = emptyList()
)