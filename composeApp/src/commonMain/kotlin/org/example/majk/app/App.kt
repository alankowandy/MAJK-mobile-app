package org.example.majk.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_login.majk_start.MajkStartScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.AdminAuthScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_containers_state.ContainerStateScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_home.HomeScreenRoot
import org.example.majk.core.presentation.components.Drawer
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileViewModel
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.AdminAuthViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.MyMedicamentScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.MyMedicamentViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.MyScheduleScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val sharedViewModel = koinViewModel<SharedViewModel>()
        val sessionStatus by sharedViewModel.sessionStatus.collectAsState()
        val userInfo by sharedViewModel.userInfo.collectAsState()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRouteTest = navBackStackEntry?.destination?.route

        val currentRoute = navBackStackEntry?.destination?.route?.let {
            routeFromString(it)
        } ?: Route.LogInGraph
        val currentGraph = navBackStackEntry?.destination?.parent?.route?.let {
            routeFromString(it)
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
                        if (currentRouteTest != "org.example.majk.app.Route.MajkStart") {
                            Text(
                                text = currentRoute.title,
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
                        if (currentRoute !is Route.MajkStart) {
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    when (currentGraph) {
                                        is Route.MajkGraph -> {
                                            scope.launch { scaffoldState.drawerState.open() }
                                        }
                                        is Route.ManageFamilyGraph -> {
                                            if (currentRouteTest == "org.example.majk.app.Route.MajkManageFamily") {
                                                scope.launch { scaffoldState.drawerState.open() }
                                            } else {
                                                navController.navigateUp()
                                            }
                                        }
                                        else -> {
                                            navController.navigateUp()
                                        }
                                    }
//                                    if (currentGraph is Route.MajkGraph) {
//                                        scope.launch { scaffoldState.drawerState.open() }
//                                    } else {
//                                        navController.navigateUp()
//                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = when (currentGraph) {
                                        is Route.MajkGraph -> Icons.Default.Menu
                                        is Route.ManageFamilyGraph -> {
                                            if (currentRouteTest == "org.example.majk.app.Route.MajkManageFamily") {
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
                        if (currentGraph is Route.MajkGraph) {
                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Diversity3,
                                    contentDescription = "family_details"
                                )
                            }
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                Drawer(
                    userInfo = userInfo,
                    currentRoute = currentRoute,
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
                        println(currentRouteTest)
                    }

                    composable<Route.MajkMySchedule>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        MyScheduleScreenRoot()
                    }

                    composable<Route.MajkHistory>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        HistoryScreenRoot()
                    }

                    composable<Route.MajkMyMedkit>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        val viewModel = koinViewModel<MyMedicamentViewModel>()

                        MyMedicamentScreenRoot(
                            viewModel = viewModel
                        )
                        println(currentRouteTest)
                    }

                    composable<Route.MajkContainersState>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) {
                        ContainerStateScreenRoot()
                    }

                    navigation<Route.ManageFamilyGraph>(
                        startDestination = Route.MajkManageFamily
                    ) {
                        composable<Route.MajkManageFamily>(
                            enterTransition = { slideInHorizontally { initialOffset ->
                                initialOffset
                            } },
                            exitTransition = { slideOutHorizontally { initialOffset ->
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