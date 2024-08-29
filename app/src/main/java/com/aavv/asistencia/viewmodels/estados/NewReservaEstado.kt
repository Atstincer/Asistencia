package com.aavv.asistencia.viewmodels.estados

import com.aavv.asistencia.constantes.Estado

data class NewReservaEstado(
    val idReservaSelected: Long = 0,
    val nombreCliente: String = "",
    val referencia: String = "",
    val adultos: String = "",
    val menores: String = "",
    val infantes: String = "",
    val agencia: String = "",
    val idAgenciaSelected: Int = 0,
    val showAgenciasToPick: Boolean = false,
    val cell: String = "",
    val mail: String = "",
    val estado: Estado = Estado.NEW
)