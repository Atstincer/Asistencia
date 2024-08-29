package com.aavv.asistencia.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aavv.asistencia.constantes.TipoAgencia
import com.aavv.asistencia.models.Agencia
import com.aavv.asistencia.models.AgenciaConReceptivo
import kotlinx.coroutines.flow.Flow

@Dao
interface AgenciaDao {

    @Upsert
    suspend fun upsertAgencia(agencia: Agencia)

    @Query("SELECT * FROM agencia")
    fun getAllAgencias(): Flow<List<Agencia>>

    @Query("DELETE FROM agencia WHERE id=:id")
    suspend fun deleteAgencia(id: Int)

    @Query("SELECT * FROM agencia WHERE id=:id")
    fun getAgencia(id: Int): Flow<Agencia>

    @Query("SELECT * FROM agencia WHERE tipo=:tipo")
    fun getAllAgencias(tipo: TipoAgencia): Flow<List<Agencia>>

//    @Query("SELECT * FROM agencia")
//    fun getAgenciaConReceptivo(): Flow<List<AgenciaConReceptivo>>

}