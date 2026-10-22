package com.example.syncpass.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import  androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
@Composable
fun ChipEstado(texto: String, fondo: Color, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        color = Color(0xFF1E293B),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(fondo, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    )
}

@Composable
fun ChipConexion(sinConexion: Boolean, pendientes: Int, modifier: Modifier = Modifier) {
    if (sinConexion) {
        ChipEstado("Sin conexion $pendientes pendientes", Color(0xFFFBBF24), modifier)
    } else {
        ChipEstado("En linea todo sincronizado", Color(0xFF86EFAC), modifier)
    }
}