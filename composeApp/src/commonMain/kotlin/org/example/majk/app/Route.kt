package org.example.majk.app

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(
    val title: String
) {

    @Serializable
    data object LogInGraph: Route(
        title = ""
    )

    @Serializable
    data object MajkGraph: Route(
        title = ""
    )

    @Serializable
    data object MajkStart: Route(
        title = ""
    )

    @Serializable
    data object MajkSignIn: Route(
        title = "Zaloguj się"
    )

    @Serializable
    data object MajkSignUp: Route(
        title = "Zarejestruj się"
    )

    @Serializable
    data object MajkRegisterDevice: Route(
        title = "Zarejestruj urządzenie"
    )

    @Serializable
    data object MajkHome: Route(
        title = "Strona główna"
    )

    @Serializable
    data object MajkMySchedule: Route(
        title = "Mój harmonogram"
    )

    @Serializable
    data object MajkHistory: Route(
        title = "Historia wydania"
    )

    @Serializable
    data object MajkMyMedkit: Route(
        title = "Moja apteczka"
    )

    @Serializable
    data object MajkContainersState: Route(
        title = "Stan pojemników"
    )

    @Serializable
    data class MajkContainerSettings(val id: String): Route(
        title = "Ustawienia pojemnika"
    )

    @Serializable
    data object ManageFamilyGraph: Route(
        title = "Zarządzaj rodziną"
    )

    @Serializable
    data object MajkManageFamily: Route(
        title = "Zarządzaj rodziną"
    )

    @Serializable
    data class MajkManageFamilySettings(val userId: Long): Route(
        title = "Zarządzaj rodziną"
    )

    @Serializable
    data object MajkAddProfile: Route(
        title = "Dodaj profil"
    )

    @Serializable
    data object MajkAdminAuth: Route(
        title = "Autoryzacja"
    )
}

fun routeFromString(route: String): Route {
    return when (route) {
        Route.LogInGraph::class.qualifiedName -> Route.LogInGraph
        Route.MajkGraph::class.qualifiedName -> Route.MajkGraph
        Route.MajkStart::class.qualifiedName -> Route.MajkStart
        Route.MajkSignIn::class.qualifiedName -> Route.MajkSignIn
        Route.MajkSignUp::class.qualifiedName -> Route.MajkSignUp
        Route.MajkRegisterDevice::class.qualifiedName -> Route.MajkRegisterDevice
        Route.MajkHome::class.qualifiedName -> Route.MajkHome
        Route.MajkMySchedule::class.qualifiedName -> Route.MajkMySchedule
        Route.MajkHistory::class.qualifiedName -> Route.MajkHistory
        Route.MajkMyMedkit::class.qualifiedName -> Route.MajkMyMedkit
        Route.MajkContainersState::class.qualifiedName -> Route.MajkContainersState
        Route.MajkManageFamily::class.qualifiedName -> Route.MajkManageFamily
        Route.MajkAddProfile::class.qualifiedName -> Route.MajkAddProfile
        Route.MajkAdminAuth::class.qualifiedName -> Route.MajkAdminAuth
        Route.ManageFamilyGraph::class.qualifiedName -> Route.ManageFamilyGraph
        else -> Route.MajkStart
    }
}