package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

data class ContainerSettingsState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchEntry: String = "",
    val searchError: String? = null,
    val isSearchExpanded: Boolean = false,
    val isEmptied: Boolean = false,
    val pillQuantityEntry: Int = 0,
    val pillQuantityEntryError: Boolean = false,
    val isSearching: Boolean = false
)