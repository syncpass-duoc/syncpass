package com.example.syncpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.syncpass.ui.navigation.Routes
import com.example.syncpass.ui.screens.home.Home
import com.example.syncpass.ui.screens.home.HomeScreen
import com.example.syncpass.ui.screens.login.LoginScreen
import com.example.syncpass.ui.theme.SyncpassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SyncpassTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = Routes.LOGIN) {
                            composable(Routes.LOGIN) {
                                LoginScreen(onLoginSuccess = {
                                    navController.navigate(Routes.HOME) {
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                })
                            }
                            composable(Routes.HOME) {
                                HomeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
