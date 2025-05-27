package org.example.majk.majk.domain

data class MedicineEntry(
    val releaseId: Long,
    val medicamentId:Long,
    val medicamentName: String,
    val startDate: String,
    val endDate: String,
    val repeatingInterval: Long,
    val pillAmount: Long,
    val mealDependability: String,
    val note: String?,
    val leafletUrl: String,
    val isOptionsRevealed: Boolean = false,
    val isTextEditorVisible: Boolean = false
)
