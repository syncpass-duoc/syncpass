package com.example.syncpass.ui.screens.escaner

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.syncpass.ui.theme.SyncpassTheme


private val Grafito = Color(0xFF1E293B)
private val Verde = Color(0xFF22C55E)
private val GrisClaro = Color(0xFFE2E8F0)

@Composable
fun EscanerScreen(
    onVolver: () -> Unit,
    onQrLeido: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grafito)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Escanear QR",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(260.dp)
                .border(4.dp, Verde, RoundedCornerShape(24.dp))
                .clickable { onQrLeido("ASISTENTE-001") },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Camara simulada\nToca para escanear",
                color = GrisClaro,
                textAlign = TextAlign.Center
            )
        }

        Text("Apunta al codigo QR del asistente", color = GrisClaro)

    }
}

@Preview(showBackground = true)
@Composable
fun EscanerScreenPreview() {
    SyncpassTheme { EscanerScreen(onVolver = {}, onQrLeido = {})
    }
}
