package org.example.majk.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInScreenRoot
import org.example.majk.majk.presentation.majk_login.majk_start.MajkStartScreenRoot

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
                        navController = navController
                    )
                }
                composable<Route.MajkSignIn> {
                    MajkSignInScreenRoot()
                }
            }
        }
    }
}