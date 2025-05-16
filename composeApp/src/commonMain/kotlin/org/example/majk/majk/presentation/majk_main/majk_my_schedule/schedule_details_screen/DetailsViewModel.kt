package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.wojciechosak.calendar.utils.today
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import org.example.majk.app.Route
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleState


class DetailsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val date =
        LocalDate.parse(savedStateHandle.toRoute<Route.MajkScheduleDetailsByDate>().date)

    private val _state = MutableStateFlow(DetailsState(
        selectedDate = date
    ))
    val state = _state.asStateFlow()

    private val _medicines = MutableStateFlow<Map<Int, List<MedicineEntry>>>(emptyMap())
    val medicines = _medicines.asStateFlow()

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.OnSelectDate -> {
                val newDate = action.date
                _state.update{
                    it.copy(
                        selectedDate = newDate,
                        schedule = emptyMap()
                    )
                }
                fetchScheduledMedicine()
            }
            is DetailsAction.OnRefreshCurrentTime -> {
                _state.update {
                    it.copy(
                        currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")),
                        currentHour = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).hour,
                        currentMinute = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).minute
                    )
                }
                println("refreshed")
            }
            is DetailsAction.OnScheduledMedicineClick -> {

            }
            is DetailsAction.OnBackClick -> {}
        }
    }

    private fun fetchScheduledMedicine() {
        viewModelScope.launch {
            runCatching {
                _state.update {
                    it.copy(
                        schedule = appRepository.fetchScheduleForDate(_state.value.selectedDate)
                    )
                }
            }
        }
    }
}