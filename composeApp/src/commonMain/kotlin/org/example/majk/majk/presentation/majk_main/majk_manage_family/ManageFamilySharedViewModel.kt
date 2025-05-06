package org.example.majk.majk.presentation.majk_main.majk_manage_family

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ManageFamilySharedViewModel: ViewModel() {

    private val _shouldRefresh = MutableStateFlow(false)
    val shouldRefresh = _shouldRefresh.asStateFlow()

    fun switchRefresh(value: Boolean) {
        _shouldRefresh.value = value
    }
}