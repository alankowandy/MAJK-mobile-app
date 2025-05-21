package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen

data class ContainerScreenState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val initialLoadDone: Boolean = false
)