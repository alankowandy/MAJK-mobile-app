package org.example.majk.majk.domain

data class ContainerState(
    val containerId: Long,
    val containerState: String,
    val pillQuantity: Double,
    val containerNumber: Long,
    val medicamentName: String
)
