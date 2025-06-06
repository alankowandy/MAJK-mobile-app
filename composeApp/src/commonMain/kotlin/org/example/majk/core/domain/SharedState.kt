package org.example.majk.core.domain

data class SharedState(
    val uuid: String? = null,
    val accountId: Long? = null,
    val username: String? = null,
    val familyId: Long? = null,
    val deviceId: Long? = null,
    val permission: String? = null,
    val email: String? = null,
    val isActionExpanded: Boolean = false,
    val errorMessage: String? = null,
    val actionErrorMessage: String? = null
)