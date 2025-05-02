package org.example.majk.majk.presentation.majk_main.majk_my_medkit

interface MyMedicamentAction {
    data object OnMedicamentDetailsClick: MyMedicamentAction
    data object OnDeleteMedicamentClick: MyMedicamentAction
    data object OnSortMedicamentClick: MyMedicamentAction
    data object OnDismissDialog: MyMedicamentAction
    data object OnAddMedicamentClick: MyMedicamentAction
}