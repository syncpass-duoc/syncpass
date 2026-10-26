package com.example.syncpass.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.syncpass.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario")
    suspend fun buscarPorNombre(nombreUsuario: String): List<UsuarioEntity>
}
