package com.aavv.asistencia.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Agencia::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idAgencia"),
    )
])
data class Reserva(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombreCliente: String,
    val referencia: String = "",
    val adultos: Int,
    val menores: Int = 0,
    val infantes: Int = 0,
    val idAgencia: Int = 0,
    val cell: String = "",
    val mail: String = "")
