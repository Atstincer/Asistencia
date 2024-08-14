package com.aavv.asistencia.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DialogConfirmEliminar(title: String, body: String,onDismiss: ()->Unit, onConfirm: ()->Unit){
    AlertDialog(
        onDismissRequest = { onDismiss() }, 
        confirmButton = { 
            TextButton(onClick = { onConfirm() }) {
                Text(text = "ELIMINAR")
            }
        },
        dismissButton = { 
            TextButton(onClick = { onDismiss() }) {
                Text(text = "CANCELAR")
            }
        },
        title = { Text(text = title) },
        text = { Text(text = body) })
}