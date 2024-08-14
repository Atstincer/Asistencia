package com.aavv.asistencia.viewmodels.estados

import com.aavv.asistencia.constantes.Destinos
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.models.Hotel

data class HotelEstado(
    val estado: Estado = Estado.NEW,
    val hotelSelected: Hotel = Hotel.getEmptyHotel(),
    val name: String = "",
    val destino: Destinos = Destinos.DESCONOCIDO,
    val mostrarDestinos: Boolean = false,
    val mostrarDialogConfirmEliminar: Boolean = false
)
