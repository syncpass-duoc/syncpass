package com.example.syncpass.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = VerdeAcreditacion,
    onPrimary = AzulGrafito,
    secondaryContainer = VerdeMenta,
    onSecondaryContainer = AzulGrafito,
    background = GrisClaro,
    onBackground = AzulGrafito,
    surface = Color.White,
    onSurface = AzulGrafito
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeAcreditacion,
    onPrimary = AzulGrafito,
    secondaryContainer = VerdeMenta,
    onSecondaryContainer = AzulGrafito,
    background = AzulGrafito,
    onBackground = GrisClaro,
    surface = AzulGrafito,
    onSurface = GrisClaro
)

@Composable
fun SyncpassTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colores = if (darkTheme) EsquemaOscuro else EsquemaClaro
    MaterialTheme(colorScheme = colores, content = content)
}