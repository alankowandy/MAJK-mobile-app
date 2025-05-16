package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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
        sharedViewModel.userInfo
            .map { it?.familyId }
            .filter { it != 0L }
            .distinctUntilChanged()
            .onEach { familyId ->
                if (familyId != null) {
                    fetchMedicamentList(familyId)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MyMedicamentAction) {
        when (action) {
            is MyMedicamentAction.OnMedicamentDetailsClick -> {}
            is MyMedicamentAction.OnDeleteMedicamentClick -> {
                deleteMedicament(action.medicamentId)
            }
            is MyMedicamentAction.OnSortMedicamentClick -> {}
            is MyMedicamentAction.OnAddMedicamentClick -> {

            }
            is MyMedicamentAction.OnDismissDialog -> {
                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun fetchMedicamentList(familyId: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            runCatching {
                val result = appRepository.fetchMedicamentList(familyId)
                _myMedicamentList.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
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
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd przy usuwaniu leku. Spróbuj ponownie."
                    )
                }
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