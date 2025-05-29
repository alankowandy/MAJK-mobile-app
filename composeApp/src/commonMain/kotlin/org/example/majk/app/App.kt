package org.example.majk.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import org.example.majk.core.domain.RouteTitle
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.SharedAction
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.core.presentation.components.ActionDropdown
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_login.majk_start.MajkStartScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.AdminAuthScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.ContainerStateScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_home.HomeScreenRoot
import org.example.majk.core.presentation.components.Drawer
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileViewModel
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.AdminAuthViewModel
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.ContainerStateViewModel
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsViewModel
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.SelectedAccountViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditAction
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.SelectedMedicineViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleAction
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.ScheduledMedicineListScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.ScheduledMedicineListViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()

        val sharedViewModel = koinInject<SharedViewModel>()
        val sessionStatus by sharedViewModel.sessionStatus.collectAsState()
        val userInfo by sharedViewModel.userInfo.collectAsStateWithLifecycle()
        val familyUsers by sharedViewModel.familyUsers.collectAsStateWithLifecycle()
        val sharedState by sharedViewModel.state.collectAsState()

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route
        val currentRouteTitle = navBackStackEntry?.destination?.route?.let {
            routeFromString(it)
        } ?: RouteTitle.MajkStart

        val currentGraph = navBackStackEntry?.destination?.parent?.route?.let {
            graphFromString(it)
        }

        LaunchedEffect(sessionStatus) {
            when (sessionStatus) {
                is SessionStatus.Authenticated -> {
                    navController.navigate(Route.MajkGraph) {
                        launchSingleTop = true
                        popUpTo(Route.LogInGraph) { inclusive = true }
                    }
                }
                SessionStatus.Initializing -> {  }
                is SessionStatus.NotAuthenticated, is SessionStatus.RefreshFailure -> {
                    navController.navigate(Route.LogInGraph) {
                        launchSingleTop = true
                        popUpTo(Route.MajkGraph) { inclusive = true }
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        if (currentRoute != "org.example.majk.app.Route.MajkStart") {
                            Text(
                                text = currentRouteTitle.title,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = OffWhite,
                        navigationIconContentColor = DarkTeal,
                        actionIconContentColor = DarkTeal,
                        titleContentColor = DarkTeal
                    ),
                    navigationIcon = {
                        if (currentRoute != "org.example.majk.app.Route.MajkStart") {
                            IconButton(
                                onClick = {
                                    when (currentGraph) {
                                        is Route.MajkGraph -> {
                                            scope.launch { scaffoldState.drawerState.open() }
                                        }
                                        is Route.ManageFamilyGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkManageFamily") {
                                                scope.launch { scaffoldState.drawerState.open() }
                                            } else {
                                                navController.navigateUp()
                                            }
                                        }
                                        is Route.MajkScheduleGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkSchedule") {
                                                scope.launch { scaffoldState.drawerState.open() }
                                            } else {
                                                navController.navigateUp()
                                            }
                                        }
                                        is Route.MyMedkitGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkMyMedkit") {
                                                scope.launch { scaffoldState.drawerState.open() }
                                            } else {
                                                navController.navigateUp()
                                            }
                                        }
                                        is Route.MajkContainerGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkContainersState") {
                                                scope.launch { scaffoldState.drawerState.open() }
                                            } else {
                                                navController.navigateUp()
                                            }
                                        }
                                        else -> {
                                            navController.navigateUp()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = when (currentGraph) {
                                        is Route.MajkGraph -> Icons.Default.Menu
                                        is Route.ManageFamilyGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkManageFamily") {
                                                Icons.Default.Menu
                                            } else {
                                                Icons.Default.ArrowBackIosNew
                                            }
                                        }
                                        is Route.MajkScheduleGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkSchedule") {
                                                Icons.Default.Menu
                                            } else {
                                                Icons.Default.ArrowBackIosNew
                                            }
                                        }
                                        is Route.MyMedkitGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkMyMedkit") {
                                                Icons.Default.Menu
                                            } else {
                                                Icons.Default.ArrowBackIosNew
                                            }
                                        }
                                        is Route.MajkContainerGraph -> {
                                            if (currentRoute == "org.example.majk.app.Route.MajkContainersState") {
                                                Icons.Default.Menu
                                            } else {
                                                Icons.Default.ArrowBackIosNew
                                            }
                                        }
                                        else -> {
                                            Icons.Default.ArrowBackIosNew
                                        }
                                    },
                                    contentDescription = "navigation icon"
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentGraph !is Route.LogInGraph) {
                            Box {
                                IconButton(
                                    onClick = {
                                        sharedViewModel.onAction(SharedAction.OnExpandAction(true))
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Diversity3,
                                        contentDescription = "family_details"
                                    )
                                }
                                ActionDropdown(
                                    familyCode = userInfo?.familyId.toString(),
                                    deviceCode = userInfo?.deviceId.toString(),
                                    familyUsers = familyUsers,
                                    isAdmin = (userInfo?.permission == "admin"),
                                    onUserClick = {

                                    },
                                    onDismiss = {
                                        sharedViewModel.onAction(SharedAction.OnExpandAction(false))
                                    },
                                    isExpanded = sharedState.isActionExpanded
                                )
                            }
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = currentGraph !is Route.LogInGraph,
            drawerContent = {
                Drawer(
                    userInfo = userInfo,
                    currentRouteTitle = currentRouteTitle,
                    onSignOutClick = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                            sharedViewModel.signOut()
                        }
                    },
                    onItemClick = { clickedRoute ->
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        navController.navigate(clickedRoute) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Route.MajkHome) {
                                inclusive = false
                                saveState = true
                            }
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.LogInGraph
            ) {
                navigation<Route.LogInGraph>(
                    startDestination = Route.MajkStart
                ) {
                    composable<Route.MajkStart>(
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() },
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        MajkStartScreenRoot(
                            onSignInClick = {
                                navController.navigate(Route.MajkSignIn)
                            },
                            onSignUpClick = {
                                navController.navigate(Route.MajkSignUp)
                            },
                            onRegisterDeviceClick = {
                                navController.navigate(Route.MajkRegisterDevice)
                            }
                        )
                    }

                    composable<Route.MajkSignIn>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<MajkSignInViewModel>()

                        MajkSignInScreenRoot(
                            viewModel = viewModel,
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }

                    composable<Route.MajkSignUp>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<MajkSignUpViewModel>()

                        MajkSignUpScreenRoot(
                            viewModel = viewModel,
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }

                    composable<Route.MajkRegisterDevice>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<MajkRegisterDeviceViewModel>()

                        MajkRegisterDeviceScreenRoot(
                            viewModel = viewModel,
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }
                }

                navigation<Route.MajkGraph>(
                    startDestination = Route.MajkHome
                ) {
                    composable<Route.MajkHome>(
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() },
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {


                        HomeScreenRoot()
                    }

                    navigation<Route.MajkScheduleGraph>(
                        startDestination = Route.MajkSchedule
                    ) {
                        composable<Route.MajkSchedule>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() },
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ScheduleViewModel>()
                            ScheduleScreenRoot(
                                viewModel = viewModel,
                                onDateClick = { date ->
                                    navController.navigate(
                                        Route.MajkScheduleDetailsByDate(date)
                                    )
                                },
                                onMedicineListClick = { accountId ->
                                    navController.navigate(
                                        Route.MajkScheduleMedicineList(accountId)
                                    )
                                },
                                onAddScheduleClick = { accountId ->
                                    navController.navigate(
                                        Route.MajkAddSchedule(accountId)
                                    )
                                }
                            )
                        }
                        composable<Route.MajkScheduleByUser>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() },
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ScheduleViewModel>()
                            ScheduleScreenRoot(
                                viewModel = viewModel,
                                onDateClick = { date ->
                                    navController.navigate(
                                        Route.MajkScheduleDetailsByDate(date)
                                    )
                                },
                                onMedicineListClick = { accountId ->
                                    navController.navigate(
                                        Route.MajkScheduleMedicineList(accountId)
                                    )
                                },
                                onAddScheduleClick = { accountId ->
                                    navController.navigate(
                                        Route.MajkAddSchedule(accountId)
                                    )
                                }
                            )
                        }
                        composable<Route.MajkScheduleDetailsByDate>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<DetailsViewModel>()

                            DetailsScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        }

                        composable<Route.MajkScheduleMedicineList>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ScheduledMedicineListViewModel>()
                            val selectedMedicineViewModel =
                                it.sharedKoinViewModel<SelectedMedicineViewModel>(navController)

                            LaunchedEffect(true) {
                                selectedMedicineViewModel.onSelectMedicine(null)
                            }

                            ScheduledMedicineListScreenRoot(
                                viewModel = viewModel,
                                onMedicineDetailsClick = { medicineEntry ->
                                    selectedMedicineViewModel.onSelectMedicine(medicineEntry)
                                    navController.navigate(
                                        Route.MajkAddSchedule(medicineEntry.accountIdForEdit)
                                    )
                                },
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        }

                        composable<Route.MajkAddSchedule>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<AddScheduleViewModel>()
                            val selectedMedicineViewModel =
                                it.sharedKoinViewModel<SelectedMedicineViewModel>(navController)
                            val selectedMedicine by selectedMedicineViewModel.selectedMedicine.collectAsStateWithLifecycle()

                            LaunchedEffect(selectedMedicine) {
                                selectedMedicine?.let { medicine ->
                                    viewModel.onAction(AddScheduleAction.OnSelectedMedicineChange(medicine))
                                }
                            }

                            AddScheduleScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        }

                    }


                    composable<Route.MajkHistory>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<HistoryViewModel>()

                        HistoryScreenRoot(
                            viewModel = viewModel
                        )
                    }

                    navigation<Route.MyMedkitGraph>(
                        startDestination = Route.MajkMyMedkit
                    ) {
                        composable<Route.MajkMyMedkit>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() },
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<MyMedicamentViewModel>()

                            MyMedicamentScreenRoot(
                                viewModel = viewModel,
                                onAddMedicamentClick = {
                                    navController.navigate(
                                        Route.MyMedkitEdit
                                    )
                                }
                            )
                        }

                        composable<Route.MyMedkitEdit>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<MyMedkitEditViewModel>()

                            MyMedkitEditScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }


                    navigation<Route.MajkContainerGraph>(
                        startDestination = Route.MajkContainersState
                    ) {
                        composable<Route.MajkContainersState>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() },
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ContainerStateViewModel>()

                            ContainerStateScreenRoot(
                                viewModel = viewModel,
                                onSettingsClick = { container ->
                                    navController.navigate(
                                        Route.MajkContainerSettings(container)
                                    )
                                }
                            )
                        }

                        composable<Route.MajkContainerSettings>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ContainerSettingsViewModel>()

                            ContainerSettingsScreenRoot(
                                viewModel = viewModel,
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                    }


                    navigation<Route.ManageFamilyGraph>(
                        startDestination = Route.MajkManageFamily
                    ) {
                        composable<Route.MajkManageFamily>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() },
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<ManageFamilyViewModel>()

                            ManageFamilyScreenRoot(
                                viewModel = viewModel,
                                onScheduleClick = { user ->
                                    navController.navigate(
                                        // do zmiany nawigacja
                                        Route.MajkManageFamilySettings(user)
                                    )
                                },
                                onSettingsClick = { user ->
                                    navController.navigate(
                                        Route.MajkManageFamilySettings(user)
                                    )
                                }
                            )
                        }

                        composable<Route.MajkManageFamilySettings>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
                                initialOffset
                            } }
                        ) {
                            val viewModel = koinViewModel<SettingsViewModel>()

                            SettingsScreenRoot(
                                viewModel = viewModel,
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                    }

                    composable<Route.MajkAddProfile>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<AddProfileViewModel>()

                        AddProfileScreenRoot(
                            viewModel = viewModel
                        )
                    }

                    composable<Route.MajkAdminAuth>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<AdminAuthViewModel>()

                        AdminAuthScreenRoot(
                            viewModel = viewModel
                        )
                    }
                }
            }

        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}