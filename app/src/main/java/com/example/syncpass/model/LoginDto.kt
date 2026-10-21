package com.example.syncpass.model

data class LoginRequest (
    val nombreUsuario: String,
    val contrasena: String
)

data class LoginResponse (
    val mostrarNombre: String,
    val tipoUsuario: TipoUsuario
)