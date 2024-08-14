package com.aavv.asistencia.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavv.asistencia.constantes.Destinos
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.models.Hotel
import com.aavv.asistencia.repositories.HotelRepository
import com.aavv.asistencia.viewmodels.estados.HotelEstado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsScreenVM @Inject constructor (private val repository: HotelRepository): ViewModel() {

    var estado by mutableStateOf(HotelEstado())
        private set

    fun setEstado(estado: Estado){
        this.estado = this.estado.copy(
            estado = estado
        )
    }

    fun setHotelSelected(hotel: Hotel){
        estado = estado.copy(
            hotelSelected = hotel
        )
    }

    fun setName(name: String){
        estado = estado.copy(
            name = name
        )
    }

    fun setDestino(destino: Destinos){
        estado = estado.copy(
            destino = destino
        )
    }

    fun mostrarDestinos(mostrar: Boolean){
        estado = estado.copy(
            mostrarDestinos = mostrar
        )
    }

    fun mostrarDialogConfirmEliminar(show: Boolean){
        estado = estado.copy(
            mostrarDialogConfirmEliminar = show
        )
    }

    fun setRegularMode(){
        setEstado(Estado.NEW)
        setName("")
        setDestino(Destinos.DESCONOCIDO)
        mostrarDestinos(false)
        setHotelSelected(Hotel.getEmptyHotel())
    }

    fun setEditarMode(hotelSelected: Hotel){
        setHotelSelected(hotelSelected)
        setEstado(Estado.EDITAR)
        setName(hotelSelected.name)
        setDestino(hotelSelected.destino)
        mostrarDestinos(false)
    }

    fun deleteHotel(){
        deleteHotel(estado.hotelSelected.id)
        mostrarDialogConfirmEliminar(false)
        setRegularMode()
    }

    fun upsertHotel(){
        upsertHotel(Hotel(
            id = estado.hotelSelected.id,
            name = estado.name,
            destino = estado.destino
        ))
        setRegularMode()
    }

    fun upsertHotel(hotel: Hotel){
        viewModelScope.launch { repository.upsertHotel(hotel) }
    }

    fun getAllHotels(): Flow<List<Hotel>>{
        return repository.getAllHotels()
    }

    fun deleteHotel(id: Int){
        viewModelScope.launch { repository.deleteHotel(id) }
    }
}