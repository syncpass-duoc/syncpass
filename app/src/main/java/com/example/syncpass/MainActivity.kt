package com.example.syncpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.syncpass.data.local.SyncPassBdd
import com.example.syncpass.data.local.insertarDatos
import com.example.syncpass.ui.navigation.Routes
import com.example.syncpass.ui.screens.home.HomeScreen
import com.example.syncpass.ui.screens.login.LoginScreen
import com.example.syncpass.ui.theme.SyncpassTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(applicationContext, SyncPassBdd::class.java, "syncpass.db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            insertarDatos(db.eventoDao())
        }
        setContent {
            val eventosFlow = remember { db.eventoDao().observarEventos() }
            val eventos by eventosFlow.collectAsState(initial = emptyList())
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
                                HomeScreen(eventos = eventos)
                            }
                        }
                    }
                }
            }
        }
    }
}
