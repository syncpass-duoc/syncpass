package com.example.syncpass.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.syncpass.data.local.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {
    @Query("SELECT * FROM eventos ORDER BY fechaHoraMillis ASC")
    fun observarEventos(): Flow<List<EventoEntity>>

    @Query("SELECT COUNT(*) FROM eventos")
    suspend fun contar(): Int

    @Upsert
    suspend fun guardar(evento: EventoEntity)
}