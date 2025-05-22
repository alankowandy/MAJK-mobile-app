package org.example.majk.core.presentation

interface SharedAction {
    data class OnExpandAction(val isExpanded: Boolean): SharedAction
    data object OnRefreshActionData: SharedAction
}