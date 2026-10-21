package com.example.syncpass.data.remote

import com.example.syncpass.domain.model.TipoUsuario

data class LoginRequest (
    val nombreUsuario: String,
    val contrasena: String
)

data class LoginResponse (
    val mostrarNombre: String,
    val tipoUsuario: TipoUsuario
)