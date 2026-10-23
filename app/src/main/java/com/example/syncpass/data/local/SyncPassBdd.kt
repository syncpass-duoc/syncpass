package com.example.syncpass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.syncpass.data.local.dao.EventoDao
import com.example.syncpass.data.local.entity.EventoEntity
import java.time.LocalDate
import java.time.ZoneId

@Database(
    entities = [EventoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class SyncPassBdd: RoomDatabase() {
    abstract fun eventoDao(): EventoDao
}

suspend fun insertarDatos(dao: EventoDao) {
    if (dao.contar() > 0 ) return

    val zona = ZoneId.systemDefault()
    val hoy = LocalDate.now()

    dao.guardar(
        EventoEntity(
            id = 1,
            titulo = "Taller: Excel y Ofimatica",
            lugar = "Unidad Vecinal N°12",
            fechaHoraMillis = hoy.atTime(10, 0).atZone(zona)
                .toInstant().toEpochMilli(),
            cantInscritos = 18,
            maxCupos = 30
        )
    )

    dao.guardar(
        EventoEntity(
            id = 2,
            titulo = "Taller: Tramites en linea",
            lugar = "Unidad Vecinal N°8",
            fechaHoraMillis = hoy.plusDays(7).atTime(15, 0).atZone(zona)
                .toInstant().toEpochMilli(),
            cantInscritos = 9,
            maxCupos = 25
        )
    )
}