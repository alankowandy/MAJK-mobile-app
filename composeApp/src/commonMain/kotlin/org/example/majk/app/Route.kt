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
    data object MajkManageFamily: Route(
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