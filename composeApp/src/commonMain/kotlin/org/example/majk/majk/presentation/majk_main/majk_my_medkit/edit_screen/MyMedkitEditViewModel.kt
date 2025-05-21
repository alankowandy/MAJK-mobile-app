package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen

import androidx.lifecycle.ViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MyMedkitEditViewModel(
    appRepository: AppRepository
): ViewModel() {

    private val _state = MutableStateFlow(MyMedkitEditState())
    val state = _state.asStateFlow()


}