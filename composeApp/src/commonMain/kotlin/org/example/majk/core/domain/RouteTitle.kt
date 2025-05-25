package org.example.majk.core.domain


sealed class RouteTitle(
    val title: String = ""
) {
    data object MajkStart: RouteTitle(
        title = ""
    )

    data object MajkSignIn: RouteTitle(
        title = "Zaloguj się"
    )

    data object MajkSignUp: RouteTitle(
        title = "Zarejestruj się"
    )

    data object MajkRegisterDevice: RouteTitle(
        title = "Zarejestruj urządzenie"
    )

    data object MajkHome: RouteTitle(
        title = "Strona główna"
    )

    data object MajkSchedule: RouteTitle(
        title = "Mój harmonogram"
    )

    data object MajkScheduleDetails: RouteTitle(
        title = "Szczegóły"
    )

    data object MajkHistory: RouteTitle(
        title = "Historia wydania"
    )

    data object MajkMyMedkit: RouteTitle(
        title = "Moja apteczka"
    )

    data object MyMedkitEdit: RouteTitle(
        title = "Dodaj lek"
    )

    data object MajkContainersState: RouteTitle(
        title = "Stan pojemników"
    )

    data object MajkContainerSettings: RouteTitle(
        title = "Ustawienia pojemnika"
    )

    data object MajkManageFamily: RouteTitle(
        title = "Zarządzaj rodziną"
    )

    data object MajkManageFamilySettings: RouteTitle(
        title = "Zarządzaj rodziną"
    )

    data object MajkAddProfile: RouteTitle(
        title = "Dodaj profil"
    )

    data object MajkAdminAuth: RouteTitle(
        title = "Autoryzacja"
    )
}