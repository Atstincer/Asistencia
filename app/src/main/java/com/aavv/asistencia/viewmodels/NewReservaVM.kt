package com.aavv.asistencia.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.models.Agencia
import com.aavv.asistencia.models.Reserva
import com.aavv.asistencia.repositories.AgenciaRepository
import com.aavv.asistencia.repositories.ReservaRepository
import com.aavv.asistencia.viewmodels.estados.NewReservaEstado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewReservaVM @Inject constructor(
    private val agenciaRepo: AgenciaRepository,
    private val reservaRepo: ReservaRepository): ViewModel() {

    var estado by mutableStateOf(NewReservaEstado())
        private set

    fun setIdReservaSelected(id: Long){
        estado = estado.copy(
            idReservaSelected = id
        )
    }

    fun setNombreCliente(nombre: String){
        estado = estado.copy(
            nombreCliente = nombre
        )
    }

    fun setReferencia(ref: String){
        estado = estado.copy(
            referencia = ref
        )
    }

    fun setAdultos(adultos: String){
        estado = estado.copy(
            adultos = adultos
        )
    }

    fun setMenores(menores: String){
        estado = estado.copy(
            menores = menores
        )
    }

    fun setInfantes(infantes: String){
        estado = estado.copy(
            infantes = infantes
        )
    }

    fun setAgencia(agencia: String){
        estado = estado.copy(
            agencia = agencia
        )
    }

    fun setShowAgenciasToPick(show: Boolean){
        estado = estado.copy(
            showAgenciasToPick = show
        )
    }

    fun setCell(cell: String){
        estado = estado.copy(
            cell = cell
        )
    }

    fun setMail(mail: String){
        estado = estado.copy(
            mail = mail
        )
    }

    fun setEstado(estado: Estado){
        this.estado = this.estado.copy(
            estado = estado
        )
    }

    fun setIdAgenciaSelected(id: Int){
        estado = estado.copy(
            idAgenciaSelected = id
        )
    }

    fun setEditarMode(){
        if(estado.idReservaSelected > 0) {
            setEstado(Estado.EDITAR)
        }
    }


    fun isValidReserva(): Boolean{
        return estado.nombreCliente.isNotEmpty() && estado.adultos.isNotEmpty()
    }

    fun upsertReserva(){
        viewModelScope.launch {
            val idNuevaReserva = reservaRepo.upsertReserva(
                Reserva(
                    id = estado.idReservaSelected,
                    nombreCliente = estado.nombreCliente,
                    referencia = estado.referencia,
                    adultos = if(estado.adultos.isNotEmpty()) estado.adultos.toInt() else 0,
                    menores = if(estado.menores.isNotEmpty()) estado.menores.toInt() else 0,
                    infantes = if(estado.infantes.isNotEmpty()) estado.infantes.toInt() else 0,
                    idAgencia = estado.idAgenciaSelected,
                    cell = estado.cell,
                    mail = estado.mail)
            )
            withContext(Dispatchers.Main){
                if(idNuevaReserva > 0){
                    setIdReservaSelected(idNuevaReserva)
                    setEditarMode()
                }
            }
            /* funciona
            setIdReservaSelected(reservaRepo.upsertReserva(
                Reserva(
                    id = estado.idReservaSelected,
                    nombreCliente = estado.nombreCliente,
                    referencia = estado.referencia,
                    adultos = if(estado.adultos.isNotEmpty()) estado.adultos.toInt() else 0,
                    menores = if(estado.menores.isNotEmpty()) estado.menores.toInt() else 0,
                    infantes = if(estado.infantes.isNotEmpty()) estado.infantes.toInt() else 0,
                    idAgencia = estado.idAgenciaSelected,
                    cell = estado.cell,
                    mail = estado.mail)
            ))*/
        }
    }

    fun getAllAgencias(): Flow<List<Agencia>>{
        return agenciaRepo.getAllAgencias();
    }
}