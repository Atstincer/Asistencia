package com.aavv.asistencia.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aavv.asistencia.models.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Upsert
    suspend fun upsertReserva(reserva: Reserva): Long

    @Query("SELECT * FROM reserva")
    fun getAllReservas(): Flow<List<Reserva>>


}