package org.example.majk.majk.presentation.majk_main.majk_add_profile

data class AddProfileState(
    val usernameEntry: String = "",
    val successMessage: String? = null,
    val usernameError: Boolean = false,
    val errorMessage: String? = null
)