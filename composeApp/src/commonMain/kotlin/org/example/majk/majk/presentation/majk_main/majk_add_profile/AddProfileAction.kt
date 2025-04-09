package org.example.majk.majk.presentation.majk_main.majk_add_profile

sealed interface AddProfileAction {
    data class OnUsernameChange(val username: String): AddProfileAction
}