package org.example.majk.majk.presentation.majk_main.majk_manage_family

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun onAction(action: ManageFamilyAction) {
        when (action) {
            is ManageFamilyAction.OnScheduleClick -> {
                _state.update {
                    it.copy(showSchedule = true)
                }
            }
            is ManageFamilyAction.OnPermissionsClick -> {
                _state.update {
                    it.copy(showPermissions = true)
                }
            }
            is ManageFamilyAction.OnEditClick -> {
                _state.update {
                    it.copy(showEdit = true)
                }
            }
            is ManageFamilyAction.OnDeleteClick -> {
                _state.update {
                    it.copy(showDelete = true)
                }
            }
            is ManageFamilyAction.DismissAction -> {
                _state.update {
                    it.copy(
                        showSchedule = false,
                        showPermissions = false,
                        showEdit = false,
                        showDelete = false
                    )
                }
            }
        }
    }

    private fun collectUsers(familyId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.collectUsers(familyId)
                _users.emit(result.map { it.asDomainModel() })
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