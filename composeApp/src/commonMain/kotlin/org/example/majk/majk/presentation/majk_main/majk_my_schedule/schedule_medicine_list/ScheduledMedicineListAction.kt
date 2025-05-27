package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list

interface ScheduledMedicineListAction {
    data object OnBackClick: ScheduledMedicineListAction
    data object OnNoteClick: ScheduledMedicineListAction
    data class OnDeleteClick(val scheduleId: Long): ScheduledMedicineListAction
    data class OnToggleNoteEditor(val releaseId: Long): ScheduledMedicineListAction
    data class OnNoteChange(val releaseId: Long, val note: String): ScheduledMedicineListAction
    data class OnSaveNoteClick(val releaseId: Long, val note: String): ScheduledMedicineListAction
}