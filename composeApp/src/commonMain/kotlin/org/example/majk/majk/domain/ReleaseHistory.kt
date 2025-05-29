package org.example.majk.majk.domain

data class ReleaseHistory(
    val releaseId: Long,
    val medicamentId: Long,
    val medicamentName: String,
    val startDate: String,
    val endDate: String,
    val dayInterval: Long,
    val pillAmount: Long,
    val releaseHistoryId: Long,
    val releaseHistoryDate: String
)
