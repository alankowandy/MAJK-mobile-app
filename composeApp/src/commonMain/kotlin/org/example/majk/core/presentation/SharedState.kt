package org.example.majk.core.presentation

data class SharedState(
    val accountId: Long? = null,
    val username: String? = null,
    val familyId: Long? = null,
    val deviceId: Long? = null,
    val errorMessage: String? = null
)