package org.example.majk.majk.presentation.majk_main.majk_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.dto.ReleaseHistoryDto
import org.example.majk.majk.domain.ReleaseHistory


class HistoryViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    private val _releaseHistory = MutableStateFlow<List<ReleaseHistory>>(emptyList())

    init {
        fetchAccountId()
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun fetchAccountId() {
        sharedViewModel.userInfo
            .map { it?.accountId }
            .filterNotNull()
            .distinctUntilChanged()
            .onEach { accountId ->
                fetchReleaseHistory(accountId)
                _state.update { it.copy(currentAccountId = accountId) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchReleaseHistory(accountId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchReleaseHistory(accountId)
                _releaseHistory.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update {
                    it.copy(
                        releaseHistory = _releaseHistory.value.sortedByDescending { it.releaseHistoryDate },
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = "Błąd"
                ) }
                println(error)
            }
        }
    }

    private fun formatAndInterpretData() {

    }

    private fun ReleaseHistoryDto.asDomainModel(): ReleaseHistory {
        return ReleaseHistory(
            releaseId = this.releaseId,
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName,
            startDate = this.startDate,
            endDate = this.endDate,
            dayInterval = this.dayInterval,
            pillAmount = this.pillAmount,
            releaseHistoryId = this.releaseHistoryId,
            releaseHistoryDate = this.releaseHistoryDate
        )
    }
}