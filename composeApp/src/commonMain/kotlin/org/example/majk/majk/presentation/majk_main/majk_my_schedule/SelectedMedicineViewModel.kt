package org.example.majk.majk.presentation.majk_main.majk_my_schedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import org.example.majk.majk.domain.MedicineEntry


class SelectedMedicineViewModel: ViewModel() {

    private val _selectedMedicine = MutableStateFlow<MedicineEntry?>(null)
    val selectedMedicine = _selectedMedicine.asStateFlow()

    fun onSelectMedicine(medicine: MedicineEntry?) {
        _selectedMedicine.value = medicine
    }
}