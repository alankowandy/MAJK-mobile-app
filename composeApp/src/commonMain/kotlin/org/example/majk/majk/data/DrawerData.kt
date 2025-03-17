package org.example.majk.majk.data

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

sealed class DrawerData(
    val title: String,
    val icon: ImageVector
) {
    data object HomePage: DrawerData(
        title = "Strona główna",
        icon = Icons.Outlined.Home
    )

    data object MySchedule: DrawerData(
        title = "Mój harmonogram",
        icon = Icons.Default.Schedule
    )

    data object History: DrawerData(
        title = "Historia wydania",
        icon = Icons.Default.History
    )

    data object MyMedkit: DrawerData(
        title = "Moja apteczka",
        icon = Icons.Outlined.MedicalServices
    )

    data object ContainerState: DrawerData(
        title = "Stan pojemników",
        icon = Icons.Outlined.Medication
    )

    data object ManageFamily: DrawerData(
        title = "Zarządzaj rodziną",
        icon = Icons.Outlined.Person
    )

    data object AddProfile: DrawerData(
        title = "Dodaj profil",
        icon = Icons.Outlined.AddCircle
    )

    data object AdminAuth: DrawerData(
        title = "Autoryzacja",
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