package org.example.majk.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.majk.majk.presentation.majk_login.majk_login_main_view.MainLoginView
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_login.majk_start.MajkStartScreenRoot
import org.example.majk.majk.presentation.majk_main.majk_main_view.MainView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.parent?.route
            ?: navBackStackEntry?.destination?.route

        NavHost(
            navController = navController,
            startDestination = Route.LogInGraph
        ) {
            navigation<Route.LogInGraph>(
                startDestination = Route.MajkStart
            ) {
                composable<Route.MajkStart>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
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
                    MainLoginView(
                        onBackClick = {
                            navController.navigateUp()
                        },
                        scaffoldContent = {
                            MajkSignInScreenRoot(
                                viewModel = viewModel,
                                onUserLogged = {
                                    navController.navigate(Route.MajkGraph)
                                },
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        },
                        currentRoute = Route.MajkSignIn
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
                    MainLoginView(
                        onBackClick = {
                            navController.navigateUp()
                        },
                        scaffoldContent = {
                            MajkSignUpScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        },
                        currentRoute = Route.MajkSignUp
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
                    MainLoginView(
                        onBackClick = {
                            navController.navigateUp()
                        },
                        scaffoldContent = {
                            MajkRegisterDeviceScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.navigateUp()
                                }
                            )
                        },
                        currentRoute = Route.MajkRegisterDevice
                    )
                }
            }
            navigation<Route.MajkGraph>(
                startDestination = Route.MajkHome
            ) {
                composable<Route.MajkHome>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    MainView(
                        currentRoute = Route.MajkHome,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkMySchedule>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkMySchedule,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkHistory>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkHistory,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkMyMedkit>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkMyMedkit,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkContainersState>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkContainersState,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkManageFamily>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkManageFamily,
                        scaffoldContent = {

                        }
                    )
                }
                composable<Route.MajkAddProfile>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    MainView(
                        currentRoute = Route.MajkAddProfile,
                        scaffoldContent = {

                        }
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
                    MainView(
                        currentRoute = Route.MajkAdminAuth,
                        scaffoldContent = {

                        }
                    )
                }
            }
        }
    }
}