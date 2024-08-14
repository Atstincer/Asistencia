package com.aavv.asistencia.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aavv.asistencia.constantes.TipoAgencia

@Entity
class Agencia (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val tipo: TipoAgencia = TipoAgencia.MAYORISTA,
    val idReceptivoAsociado: Int = 0,
    val activa: Boolean = true){

    companion object{
        fun getEmptyAgencia(): Agencia{
            return Agencia(name = "")
        }
    }
}