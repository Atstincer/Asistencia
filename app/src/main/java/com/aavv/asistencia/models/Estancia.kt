package com.aavv.asistencia.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Reserva::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("reservaId"),
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Hotel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idHotel"),
        onDelete = ForeignKey.CASCADE
    )
])
data class Estancia(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reservaId: Long,
    val idHotel: Int,
    val fechaIn: String,
    val fechaOut: String,
    val observaciones: String = ""
)