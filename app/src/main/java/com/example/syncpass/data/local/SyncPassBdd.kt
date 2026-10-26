package com.example.syncpass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.syncpass.data.local.dao.EventoDao
import com.example.syncpass.data.local.dao.UsuarioDao
import com.example.syncpass.data.local.entity.EventoEntity
import com.example.syncpass.data.local.entity.UsuarioEntity
import java.time.LocalDate
import java.time.ZoneId

@Database(
    entities = [EventoEntity::class, UsuarioEntity::class],
    version = 2,
    exportSchema = false
)

abstract class SyncPassBdd: RoomDatabase() {
    abstract fun eventoDao(): EventoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `usuarios` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `nombreUsuario` TEXT NOT NULL,
                        `contrasena` TEXT NOT NULL,
                        `tipoUsuario` TEXT NOT NULL
                    )""".trimIndent()
                )
            }
        }
    }
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
