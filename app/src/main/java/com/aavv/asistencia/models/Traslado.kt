package com.aavv.asistencia.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.aavv.asistencia.constantes.Aeropuerto
import com.aavv.asistencia.constantes.TipoTRF

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Reserva::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("reservaId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Hotel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("origenHotelId"),
            onDelete = ForeignKey.SET_DEFAULT
        ),
        ForeignKey(
            entity = Hotel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("destinoHotelId"),
            onDelete = ForeignKey.SET_DEFAULT
        )
    ]
)
data class Traslado(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reservaId: Long,
    val fecha: String,
    val hora: String,
    val tipoTRF: TipoTRF,
    val origenHotelId: Long = 0,
    val destinoHotelId: Long = 0,
    val aeropuerto: Aeropuerto? = null,
    val chequeado: Boolean = false,
    val confirmado: Boolean = false
)
