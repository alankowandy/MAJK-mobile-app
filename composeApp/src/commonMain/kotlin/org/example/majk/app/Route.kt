package org.example.majk.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object LogInGraph: Route

    @Serializable
    data object MajkGraph: Route

    @Serializable
    data object MajkStart: Route

    @Serializable
    data object MajkSignIn: Route

    @Serializable
    data object MajkSignUp: Route

    @Serializable
    data object MajkHome: Route
}