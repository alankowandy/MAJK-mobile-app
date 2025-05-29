package org.example.majk.majk.domain

data class ReleaseSchedule(
    val releaseId: Long,
    val medicamentId:Long,
    val medicamentName: String,
    val startDate: String,
    val endDate: String,
    val repeatingInterval: Long,
    val pillAmount: Long,
    val mealDependability: String,
    val releaseHistoryId: Long? = null,
    val releaseDateTime: String? = null
)
