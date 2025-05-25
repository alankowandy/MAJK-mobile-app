package org.example.majk.majk.domain

data class MedicamentSearch(
    val medicamentId: Long,
    val medicamentName: String,
    val medicamentType: String,
    val medicamentLeaflet: String
)