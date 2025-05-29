package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

import org.example.majk.majk.domain.ManageFamily

data class ManageFamilyState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val initialLoadDone: Boolean = false,
    val users: List<ManageFamily> = emptyList()
)