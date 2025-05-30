package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

interface MyMedicamentAction {
    data class OnDeleteMedicamentClick(val medicamentId: Long): MyMedicamentAction
    data class OnShowConfirmDialog(val medicamentId: Long, val medicamentName: String): MyMedicamentAction
    data object OnDismissErrorDialog: MyMedicamentAction
    data object OnDismissConfirmDialog: MyMedicamentAction
    data object OnAddMedicamentClick: MyMedicamentAction
    data object OnRefreshData: MyMedicamentAction
}