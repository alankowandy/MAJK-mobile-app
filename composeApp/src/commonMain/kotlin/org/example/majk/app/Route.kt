package org.example.majk.app

import kotlinx.serialization.Serializable
import org.example.majk.core.domain.RouteTitle

@Serializable
sealed interface Route {

    /** Logging in **/
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
    data object MajkRegisterDevice: Route

    /** Main app **/
    @Serializable
    data object MajkHome: Route

    @Serializable
    data object MajkScheduleGraph: Route

    @Serializable
    data object MajkSchedule: Route

    @Serializable
    data class MajkScheduleDetailsByDate(val date: String): Route

    @Serializable
    data object MajkHistory: Route

    @Serializable
    data object MyMedkitGraph: Route

    @Serializable
    data object MajkMyMedkit: Route

    @Serializable
    data object MyMedkitEdit: Route

    @Serializable
    data object MajkContainerGraph: Route

    @Serializable
    data object MajkContainersState: Route

    @Serializable
    data class MajkContainerSettings(val containerId: Long): Route

    @Serializable
    data object ManageFamilyGraph: Route

    @Serializable
    data object MajkManageFamily: Route

    @Serializable
    data class MajkManageFamilySettings(val userId: Long): Route

    @Serializable
    data object MajkAddProfile: Route

    @Serializable
    data object MajkAdminAuth: Route
}

fun graphFromString(route: String): Route {
    return when (route) {
        Route.LogInGraph::class.qualifiedName -> Route.LogInGraph
        Route.MajkGraph::class.qualifiedName -> Route.MajkGraph
        Route.ManageFamilyGraph::class.qualifiedName -> Route.ManageFamilyGraph
        Route.MajkScheduleGraph::class.qualifiedName -> Route.MajkScheduleGraph
        Route.MyMedkitGraph::class.qualifiedName -> Route.MyMedkitGraph
        Route.MajkContainerGraph::class.qualifiedName -> Route.MajkContainerGraph
        else -> Route.LogInGraph
    }
}

fun routeFromString(route: String): RouteTitle {
    return when (route) {
        Route.MajkStart::class.qualifiedName -> RouteTitle.MajkStart
        Route.MajkSignIn::class.qualifiedName -> RouteTitle.MajkSignIn
        Route.MajkSignUp::class.qualifiedName -> RouteTitle.MajkSignUp
        Route.MajkRegisterDevice::class.qualifiedName -> RouteTitle.MajkRegisterDevice
        Route.MajkHome::class.qualifiedName -> RouteTitle.MajkHome
        Route.MajkSchedule::class.qualifiedName -> RouteTitle.MajkSchedule
        Route.MajkScheduleDetailsByDate::class.qualifiedName.plus("/{date}") -> RouteTitle.MajkScheduleDetails
        Route.MajkHistory::class.qualifiedName -> RouteTitle.MajkHistory
        Route.MajkMyMedkit::class.qualifiedName -> RouteTitle.MajkMyMedkit
        Route.MyMedkitEdit::class.qualifiedName -> RouteTitle.MyMedkitEdit
        Route.MajkContainersState::class.qualifiedName -> RouteTitle.MajkContainersState
        Route.MajkContainerSettings::class.qualifiedName.plus("/{containerId}") -> RouteTitle.MajkContainerSettings
        Route.MajkManageFamily::class.qualifiedName -> RouteTitle.MajkManageFamily
        Route.MajkManageFamilySettings::class.qualifiedName.plus("/{userId}") -> RouteTitle.MajkManageFamilySettings
        Route.MajkAddProfile::class.qualifiedName -> RouteTitle.MajkAddProfile
        Route.MajkAdminAuth::class.qualifiedName -> RouteTitle.MajkAdminAuth
        else -> RouteTitle.MajkStart
    }
}