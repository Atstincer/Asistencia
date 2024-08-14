package com.aavv.asistencia.viewmodels.estados

import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.constantes.TipoAgencia
import com.aavv.asistencia.models.Agencia

data class AgenciaEstado(
    val name: String = "",
    val tipo: TipoAgencia = TipoAgencia.MAYORISTA,
    val receptivo: Agencia = Agencia.getEmptyAgencia(),
    val showLayoutPickReceptivo: Boolean = false,
    val activa: Boolean = true,
    val estado: Estado = Estado.NEW,
    val agenciaSelected: Agencia = Agencia.getEmptyAgencia(),
    val showDialogConfirEliminar: Boolean = false
)
