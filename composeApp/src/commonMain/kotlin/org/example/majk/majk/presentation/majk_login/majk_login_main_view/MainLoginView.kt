package org.example.majk.majk.presentation.majk_login.majk_login_main_view

import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import org.example.majk.app.Route
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLoginView(
    onBackClick: () -> Unit,
    scaffoldContent: @Composable () -> Unit,
    currentRoute: Route
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = currentRoute.title,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = DarkTeal,
                    navigationIconContentColor = DarkTeal,
                    containerColor = OffWhite
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ){
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) {
        scaffoldContent()
    }
}