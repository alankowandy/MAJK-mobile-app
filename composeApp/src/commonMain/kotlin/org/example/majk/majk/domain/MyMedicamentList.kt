package org.example.majk.majk.domain

data class MyMedicamentList(
    val medicamentId: Long,
    val medicamentName: String,
    val isOptionsRevealed: Boolean = false
)