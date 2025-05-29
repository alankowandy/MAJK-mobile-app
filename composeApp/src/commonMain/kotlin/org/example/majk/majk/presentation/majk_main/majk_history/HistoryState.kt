package org.example.majk.majk.presentation.majk_main.majk_history

import org.example.majk.majk.domain.ReleaseHistory

data class HistoryState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val currentAccountId: Long = 0L,
    val releaseHistory: List<ReleaseHistory> = emptyList(),
    val medicineTaken: Boolean = false
)