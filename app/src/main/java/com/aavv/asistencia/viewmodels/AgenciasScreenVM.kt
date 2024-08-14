package com.aavv.asistencia.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.constantes.TipoAgencia
import com.aavv.asistencia.models.Agencia
import com.aavv.asistencia.repositories.AgenciaRepository
import com.aavv.asistencia.viewmodels.estados.AgenciaEstado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgenciasScreenVM @Inject constructor(private val repository: AgenciaRepository): ViewModel() {

    var estado by mutableStateOf(AgenciaEstado())
        private set


    fun getTextLabelReceptivo(): String{
        return if (estado.receptivo.id <= 0) "receptivo: NINGUNO" else "receptivo: ${estado.receptivo.name}"
    }

    fun setEstado(estado: Estado){
        this.estado = this.estado.copy(
            estado = estado
        )
    }

    fun setAgenciaSelected(selected: Agencia){
        estado = estado.copy(
            agenciaSelected = selected
        )
    }

    fun setEditarMode(agencia: Agencia){
        setAgenciaSelected(agencia)
        setName(agencia.name)
        setTipo(agencia.tipo)
        setActiva(agencia.activa)
        setEstado(Estado.EDITAR)
        setReceptivo(getAgencia(agencia.idReceptivoAsociado))
    }

    fun setRegularMode(){
        setAgenciaSelected(Agencia.getEmptyAgencia())
        setName("")
        setTipo(TipoAgencia.MAYORISTA)
        setActiva(true)
        setEstado(Estado.NEW)
        setReceptivo(Agencia.getEmptyAgencia())
    }

    fun eliminarAgencia(){
        deleteAgencia(estado.agenciaSelected.id)
        setRegularMode()
    }

    fun getLabelMainButton(): String{
        return when(estado.estado){
            Estado.NEW -> "REGISTRAR"
            Estado.EDITAR -> "EDITAR"
        }
    }

    fun setName(name: String){
        estado = estado.copy(
            name = name
        )
    }

    fun showDialogConfirmEliminar(show: Boolean){
        estado = estado.copy(
            showDialogConfirEliminar = show
        )
    }

    fun setTipo(tipo: TipoAgencia){
        estado = estado.copy(
            tipo = tipo
        )
    }

    fun setReceptivo(receptivo: Agencia){
        estado = estado.copy(
            receptivo = receptivo
        )
    }

    fun setShowLayoutPickReceptivo(show: Boolean){
        estado = estado.copy(
            showLayoutPickReceptivo = show
        )
    }

    fun setActiva(activa: Boolean){
        estado = estado.copy(
            activa = activa
        )
    }

    fun upsertAgencia(){
        upsertAgencia(Agencia(
            id = estado.agenciaSelected.id,
            name = estado.name,
            tipo = estado.tipo,
            idReceptivoAsociado = estado.receptivo.id,
            activa = estado.activa))
        setRegularMode()
    }

    fun upsertAgencia(agencia: Agencia) {
        viewModelScope.launch { repository.upsertAgencia(agencia) }
    }

    fun getAllAgencias(): Flow<List<Agencia>>{
        return repository.getAllAgencias()
    }

    fun deleteAgencia(id: Int){
        viewModelScope.launch { repository.deleteAgencia(id) }
    }

    fun getAllAgencias(tipo: TipoAgencia): Flow<List<Agencia>>{
        return repository.getAllAgencias(tipo)
    }

    fun getAgencia(id: Int): Agencia{
        return repository.getAgencia(id)
    }

    /*fun getAgenciaConReceptivo(): Flow<List<AgenciaConReceptivo>>{
        return repository.getAgenciaConReceptivo()
    }*/

}