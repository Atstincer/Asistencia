package com.aavv.asistencia.repositories

import com.aavv.asistencia.daos.ReservaDao
import com.aavv.asistencia.models.Reserva
import kotlinx.coroutines.flow.Flow

class ReservaRepository(val reservaDao: ReservaDao) {

    suspend fun upsertReserva(reserva: Reserva): Long = reservaDao.upsertReserva(reserva)

    fun getAllReservas(): Flow<List<Reserva>>{
        return reservaDao.getAllReservas()
    }
}