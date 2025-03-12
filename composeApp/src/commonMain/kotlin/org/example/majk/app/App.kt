package org.example.majk.app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_login.majk_start.MajkStartScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.LogInGraph
        ) {
            navigation<Route.LogInGraph>(
                startDestination = Route.MajkStart
            ) {
                composable<Route.MajkStart> {
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
                composable<Route.MajkSignIn> {
                    val viewModel = koinViewModel<MajkSignInViewModel>()
                    MajkSignInScreenRoot(
                        viewModel = viewModel,
                        onUserLogged = {
                            navController.navigate(Route.MajkGraph)
                        },
                        onBackClick = {
                            navController.navigate(Route.MajkStart)
                        }
                    )
                }
                composable<Route.MajkSignUp> {
                    val viewModel = koinViewModel<MajkSignUpViewModel>()
                    MajkSignUpScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigate(Route.MajkStart)
                        }
                    )
                }
                composable<Route.MajkRegisterDevice> {
                    val viewModel = koinViewModel<MajkRegisterDeviceViewModel>()
                    MajkRegisterDeviceScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigate(Route.MajkStart)
                        }
                    )
                }
            }
            navigation<Route.MajkGraph>(
                startDestination = Route.MajkHome
            ) {
                composable<Route.MajkHome> {
                    Column {

                    }
                }
            }
        }
    }
}