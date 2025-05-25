package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

data class ContainerSettingsState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val initialSearchEntry: String = "",
    val selectedMedicamentId: Long = 0,
    val searchError: String? = null,
    val isSearchExpanded: Boolean = false,
    val isEmptied: Boolean = false,
    val initialPillQuantity: Int = 0,
    val pillQuantityEntry: Int = 0,
    val pillQuantityEntryError: Boolean = false,
    val isSearching: Boolean = false
)