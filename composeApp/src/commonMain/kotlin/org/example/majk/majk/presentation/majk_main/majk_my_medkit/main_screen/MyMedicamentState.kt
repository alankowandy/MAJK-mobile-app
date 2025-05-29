package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

import org.example.majk.majk.domain.MyMedicamentList

data class MyMedicamentState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val initialLoadDone: Boolean = false,
    val currentFamilyId: Long = 0L,
    val myMedicamentList: List<MyMedicamentList> = emptyList()
)