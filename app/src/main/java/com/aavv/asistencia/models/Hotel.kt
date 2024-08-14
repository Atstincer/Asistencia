package com.aavv.asistencia.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aavv.asistencia.constantes.Destinos

@Entity
data class Hotel(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name:String, val destino: Destinos){

    companion object {
        fun getEmptyHotel(): Hotel{
            return Hotel(name = "", destino = Destinos.DESCONOCIDO)
        }
    }
}
