package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.domain.ManageFamily

class ManageFamilyViewModel(
    private val appRepository: AppRepository
): ViewModel() {

    private val _state = MutableStateFlow(ManageFamilyState())
    val state = _state.asStateFlow()

    private val _users = MutableStateFlow<List<ManageFamily>>(listOf())
    val users = _users.asStateFlow()

    init {
        collectUsers(1)
    }

    fun onAction(action: ManageFamilyAction) {
        when (action) {
            is ManageFamilyAction.OnScheduleClick -> {

            }
            is ManageFamilyAction.OnSettingsClick -> {

            }
        }
    }

    private fun collectUsers(familyId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.collectUsers(familyId)
                _users.emit(result.map { it.asDomainModel() })
            }.onFailure { error ->

                println(error)
            }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun ManageFamilyDto.asDomainModel(): ManageFamily {
        return ManageFamily(
            id = this.userId,
            username = this.username
        )
    }
}