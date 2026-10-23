package com.example.syncpass.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncpass.data.local.entity.EventoEntity
import com.example.syncpass.ui.components.EventoCard
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(eventos: List<EventoEntity>) {
    val zona = ZoneId.systemDefault()
    val hoy = LocalDate.now(zona)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Eventos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        eventos.forEach { evento ->
            val fechaHora = Instant.ofEpochMilli(evento.fechaHoraMillis).atZone(zona)

            EventoCard(
                titulo = evento.titulo,
                lugar = evento.lugar,
                fecha = fechaHora.format(DateTimeFormatter.ofPattern("dd/MM")),
                hora = fechaHora.format(DateTimeFormatter.ofPattern("HH:mm")),
                cantInscritos = evento.cantInscritos,
                maxCupos = evento.maxCupos,
                esHoy = fechaHora.toLocalDate() == hoy
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

