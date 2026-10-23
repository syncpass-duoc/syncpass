package com.example.syncpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.syncpass.ui.screens.login.LoginScreen
import com.example.syncpass.ui.theme.SyncpassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SyncpassTheme {
                Scaffold { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        LoginScreen(onLoginSuccess = { })
                    }
                }
            }
        }
    }
}