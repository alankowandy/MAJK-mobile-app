package org.example.majk.majk.presentation.majk_main.majk_my_medkit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedAccountViewModel: ViewModel() {

    private val _selectedAccount = MutableStateFlow<Long>(-1)
    val selectedAccount = _selectedAccount.asStateFlow()

    fun onSelectAccount(accountId: Long?) {
        if (accountId != null) {
            _selectedAccount.value = accountId
        }
    }
}