package org.example.majk.core.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.majk.app.Route

sealed class DrawerData(
    val title: String,
    val route: Route,
    val icon: ImageVector
) {
    data object HomePage: DrawerData(
        title = "Strona główna",
        route = Route.MajkHome,
        icon = Icons.Outlined.Home
    )

    data object MySchedule: DrawerData(
        title = "Mój harmonogram",
        route = Route.MajkSchedule,
        icon = Icons.Default.Schedule
    )

    data object History: DrawerData(
        title = "Historia wydania",
        route = Route.MajkHistory,
        icon = Icons.Default.History
    )

    data object MyMedkit: DrawerData(
        title = "Moja apteczka",
        route = Route.MajkMyMedkit,
        icon = Icons.Outlined.MedicalServices
    )

    data object ContainerState: DrawerData(
        title = "Stan pojemników",
        route = Route.MajkContainersState,
        icon = Icons.Outlined.Medication
    )

    data object ManageFamily: DrawerData(
        title = "Zarządzaj rodziną",
        route = Route.MajkManageFamily,
        icon = Icons.Outlined.Person
    )

    data object AddProfile: DrawerData(
        title = "Dodaj profil",
        route = Route.MajkAddProfile,
        icon = Icons.Outlined.AddCircle
    )

    data object AdminAuth: DrawerData(
        title = "Autoryzacja",
        route = Route.MajkAdminAuth,
        icon = Icons.Outlined.Key
    )
}

val itemsInDrawer = listOf(
    DrawerData.HomePage,
    DrawerData.MySchedule,
    DrawerData.History,
    DrawerData.MyMedkit,
    DrawerData.ContainerState,
    DrawerData.ManageFamily,
    DrawerData.AddProfile,
    DrawerData.AdminAuth
)