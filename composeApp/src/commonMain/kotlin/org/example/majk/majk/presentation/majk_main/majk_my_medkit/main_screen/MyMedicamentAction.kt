package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

interface MyMedicamentAction {
    data object OnMedicamentDetailsClick: MyMedicamentAction
    data class OnDeleteMedicamentClick(val medicamentId: Long): MyMedicamentAction
    data object OnSortMedicamentClick: MyMedicamentAction
    data object OnDismissDialog: MyMedicamentAction
    data object OnAddMedicamentClick: MyMedicamentAction
}