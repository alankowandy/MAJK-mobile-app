package org.example.majk.majk.domain

data class ContainerSettings(
    val containerId: Long = 0,
    val containerState: String = "pusty",
    val medicamentName: String = "",
    val containerNumber: Long = 0,
    val pillQuantity: Long = 0
)
