package org.example.majk.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.example.majk.majk.presentation.majk_start.MajkStart

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
                    MajkStart(
                        navController = navController
                    )
                }
            }
        }
    }
}