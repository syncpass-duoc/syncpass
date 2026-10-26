package com.example.syncpass

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.syncpass.data.local.SyncPassBdd
import com.example.syncpass.data.local.insertarDatos
import com.example.syncpass.data.local.entity.UsuarioEntity
import com.example.syncpass.ui.components.BarraInferior
import com.example.syncpass.ui.navigation.Routes
import com.example.syncpass.ui.screens.escaner.EscanerScreen
import com.example.syncpass.ui.screens.home.HomeScreen
import com.example.syncpass.ui.screens.login.LoginScreen
import com.example.syncpass.ui.screens.registro.RegistroScreen
import com.example.syncpass.ui.theme.SyncpassTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(applicationContext, SyncPassBdd::class.java, "syncpass.db")
            .addMigrations(SyncPassBdd.MIGRATION_1_2)
            .build()
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
                val backStackEntry by navController.currentBackStackEntryAsState()
                val rutaActual = backStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (rutaActual == Routes.HOME ||
                            rutaActual == Routes.ESCANER ||
                            rutaActual == Routes.PENDIENTES
                        ) {
                            BarraInferior(
                                rutaActual = rutaActual,
                                onNavegar = { ruta ->
                                    if (ruta != rutaActual) {
                                        navController.navigate(ruta) {
                                            popUpTo(Routes.HOME) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = Routes.LOGIN) {
                            composable(Routes.LOGIN) {
                                LoginScreen(
                                    onAutenticar = { nombre, contrasena, tipo ->
                                        db.usuarioDao().buscarPorNombre(nombre).any { usuario ->
                                            usuario.contrasena == contrasena &&
                                                usuario.tipoUsuario == tipo
                                        }
                                    },
                                    onLoginSuccess = {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    },
                                    onRegistroClick = { navController.navigate(Routes.REGISTRO) }
                                )
                            }
                            composable(Routes.REGISTRO) {
                                RegistroScreen(
                                    onRegistrar = { nombre, contrasena, tipo ->
                                        db.usuarioDao().insertar(
                                            UsuarioEntity(
                                                nombreUsuario = nombre,
                                                contrasena = contrasena,
                                                tipoUsuario = tipo
                                            )
                                        )
                                    },
                                    onRegistroSuccess = {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    },
                                    onVolverClick = { navController.popBackStack() }
                                )
                            }
                            composable(Routes.HOME) {
                                HomeScreen(eventos = eventos)
                            }
                            composable(Routes.ESCANER) {
                                EscanerScreen(
                                    onQrLeido = { codigo ->
                                        Toast.makeText(
                                            this@MainActivity,
                                            "QR leído: $codigo",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                            composable(Routes.PENDIENTES) {
                                Text("Pendientes", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
