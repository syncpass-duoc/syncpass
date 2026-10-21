package com.example.syncpass.data.remote.dto

import com.example.syncpass.domain.model.RolUsuario

data class LoginRequest (
    val nombreUsuario: String,
    val contrasena: String
)

data class LoginResponse (
    val mostrarNombre: String,
    val rolUsuario: RolUsuario
)