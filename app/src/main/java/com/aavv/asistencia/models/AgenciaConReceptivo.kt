package com.aavv.asistencia.models

import androidx.room.Embedded
import androidx.room.Relation

data class AgenciaConReceptivo(
    @Embedded val agencia: Agencia,
    @Relation(
        parentColumn = "id",
        entityColumn = "idReceptivoAsociado"
    ) val receptivo: Agencia?
)
