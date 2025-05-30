package org.example.majk.majk.domain

data class MyMedicamentList(
    val medicamentId: Long,
    val medicamentName: String,
    val medicamentType: String,
    val leafletUrl: String,
    val isOptionsRevealed: Boolean = false
)