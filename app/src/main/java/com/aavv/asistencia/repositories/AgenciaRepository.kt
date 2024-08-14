package com.aavv.asistencia.repositories

import androidx.room.Query
import com.aavv.asistencia.constantes.TipoAgencia
import com.aavv.asistencia.daos.AgenciaDao
import com.aavv.asistencia.models.Agencia
import kotlinx.coroutines.flow.Flow

class AgenciaRepository (val agenciaDao: AgenciaDao) {

    suspend fun upsertAgencia(agencia: Agencia) = agenciaDao.upsertAgencia(agencia)

    fun getAllAgencias() = agenciaDao.getAllAgencias()

    suspend fun deleteAgencia(id: Int) = agenciaDao.deleteAgencia(id)

    fun getAgencia(id: Int): Agencia {
        val agencia = agenciaDao.getAgencia(id)
        return if(agencia != null) agencia else Agencia.getEmptyAgencia()
    }

    fun getAllAgencias(tipo: TipoAgencia) = agenciaDao.getAllAgencias(tipo)

//    fun getAgenciaConReceptivo() = agenciaDao.getAgenciaConReceptivo()

}