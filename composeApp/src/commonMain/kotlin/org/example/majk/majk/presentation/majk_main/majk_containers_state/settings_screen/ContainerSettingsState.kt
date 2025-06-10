package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

import org.example.majk.majk.domain.ContainerSettings

data class ContainerSettingsState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val initialSearchEntry: String = "",
    val initialSelectedMedicineId: Long? = null,
    val selectedMedicamentId: Long = 0,
    val searchError: String? = null,
    val isSearchExpanded: Boolean = false,
    val isEmptied: Boolean = false,
    val initialPillQuantity: Long = 0,
    val pillQuantity: String = "0",
    val pillQuantityEntry: String = "0",
    val pillQuantityEntryError: Boolean = false,
    val isSearching: Boolean = false,
    val containerSettings: ContainerSettings = ContainerSettings(),
    val isEmptyContainerDialogVisible: Boolean = false
)