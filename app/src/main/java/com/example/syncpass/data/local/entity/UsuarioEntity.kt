package com.example.syncpass.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.syncpass.domain.model.TipoParticipante

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreUsuario: String,
    val contrasena: String,
    val tipoUsuario: TipoParticipante
)