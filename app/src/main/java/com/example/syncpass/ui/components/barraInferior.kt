package com.example.syncpass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.syncpass.ui.navigation.Routes

private data class Opcion (
    val ruta: String,
    val nombre: String,
    val icono: ImageVector
)

private val opciones = listOf(
    Opcion(Routes.HOME, "Home/Eventos", Icons.Filled.Event),
    Opcion(Routes.ESCANER, "Escaner", Icons.Filled.QrCodeScanner),
    Opcion(Routes.PENDIENTES, "Pendientes", Icons.Filled.Schedule)
)

@Composable
fun BarraInferior(
    rutaActual: String?,
    onNavegar: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        opciones.forEach { opcion ->
            val opcSeleccionada = rutaActual == opcion.ruta

            Box(
                modifier = Modifier.weight(1f).height(44.dp)
                    .background( if (opcSeleccionada) Color(0xFF86EFAC) else Color.Transparent, RoundedCornerShape(10.dp))
                    .selectable(
                        selected = opcSeleccionada,
                        role = Role.Tab,
                        onClick = { onNavegar(opcion.ruta) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = opcion.icono,
                    contentDescription = opcion.nombre,
                    tint = Color(0xFF1E293B)
                )
            }
        }
    }
}
