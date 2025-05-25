package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.data.dto.MyMedicamentListDto
import org.example.majk.majk.domain.MyMedicamentList

class MyMedicamentViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
):ViewModel() {

    private val _state = MutableStateFlow(MyMedicamentState())
    val state = _state.asStateFlow()

    private val _myMedicamentList = MutableStateFlow<List<MyMedicamentList>>(listOf())
    val myMedicamentList = _myMedicamentList.asStateFlow()

    init {
        fetchAccountDetails()
    }

    fun onAction(action: MyMedicamentAction) {
        when (action) {
            is MyMedicamentAction.OnDeleteMedicamentClick -> {
                deleteMedicament(action.medicamentId)
            }
            is MyMedicamentAction.OnSortMedicamentClick -> {
                TODO()
            }
            is MyMedicamentAction.OnAddMedicamentClick -> {
                /** Navigate to add medicament screen. Done in screen root **/
            }
            is MyMedicamentAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
            is MyMedicamentAction.OnRefreshData -> {
                _state.update { it.copy(isLoading = true) }
                fetchMedicamentList(_state.value.currentFamilyId)
            }
        }
    }

    private fun fetchAccountDetails() {
        sharedViewModel.userInfo
            .filterNotNull()
            .distinctUntilChanged()
            .onEach { shared ->
                val familyId = shared.familyId
                if (familyId != null) {
                    fetchMedicamentList(familyId)
                    _state.update {
                        it.copy(
                            currentFamilyId = familyId,
                            initialLoadDone = true
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchMedicamentList(familyId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchMedicamentList(familyId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.medicamentId }
                _myMedicamentList.emit(result)
            }.onSuccess {
                _state.update { it.copy(isLoading = false) }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd w pobieraniu danych."
                    )
                }
            }
        }
    }

    private fun deleteMedicament(medicamentId: Long) {
        viewModelScope.launch {
            runCatching {
                appRepository.deleteMedicament(medicamentId)
            }.onSuccess {
               fetchMedicamentList(_state.value.currentFamilyId)
            }.onFailure { error ->
                if (error.message?.contains("violates foreign key") == true) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Lek, który próbujesz usunąć jest w pojemniku lub ma ustawioną dawkę."
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Błąd przy usuwaniu leku. Spróbuj ponownie."
                        )
                    }
                }
                println(error)
            }
        }
    }

    private fun MyMedicamentListDto.asDomainModel(): MyMedicamentList {
        return MyMedicamentList(
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName
        )
    }
}