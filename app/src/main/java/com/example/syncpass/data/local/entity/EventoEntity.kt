package com.example.syncpass.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val lugar: String,
    val fechaHoraMillis: Long,
    val cantInscritos: Int,
    val maxCupos: Int
)
