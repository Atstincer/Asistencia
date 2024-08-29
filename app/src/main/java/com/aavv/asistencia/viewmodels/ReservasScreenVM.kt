package com.aavv.asistencia.viewmodels

import androidx.lifecycle.ViewModel
import com.aavv.asistencia.models.Reserva
import com.aavv.asistencia.repositories.ReservaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReservasScreenVM @Inject constructor(val reservaRepository: ReservaRepository): ViewModel()  {

    fun getAllReservas(): Flow<List<Reserva>>{
        return reservaRepository.getAllReservas()
    }
}