package com.aavv.asistencia.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.constantes.TipoAgencia
import com.aavv.asistencia.models.Agencia
import com.aavv.asistencia.viewmodels.AgenciasScreenVM

@Composable
fun AgenciasScreen(vm: AgenciasScreenVM = hiltViewModel()){
    val scrollState = rememberScrollState()
    BackHandler(enabled = vm.estado.estado == Estado.EDITAR) {
        vm.setRegularMode()
    }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = { MyTopAppBar() }) {innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(all = 10.dp)
            .fillMaxWidth()
            .scrollable(state = scrollState, orientation = Orientation.Vertical)) {
            LayoutNewAgencia()
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp))
            LayoutAgenciasRegistradas()
        }
        if(vm.estado.showDialogConfirEliminar){
            DialogConfirmEliminar(
                "Confirmar",
                "Seguro que desea eliminar '${vm.estado.agenciaSelected.name}'?",
                onDismiss = { vm.showDialogConfirmEliminar(false) }) {
                vm.eliminarAgencia()
                vm.showDialogConfirmEliminar(false)
        } }
    }
}

@Composable
fun LayoutNewAgencia(vm: AgenciasScreenVM = hiltViewModel()){
    val estado = vm.estado
    Column {
        TextField(
            value = estado.name,
            onValueChange = {vm.setName(it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Agencia")},
            maxLines = 1,
            trailingIcon = {IconButton(onClick = { vm.setName("") }) {
                Icon(Icons.Filled.Clear, contentDescription = "Borrar campo")
            }})
        LayoutTipoAgencia()
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(alignment = Alignment.End)) {
            Checkbox(checked = estado.activa, onCheckedChange = {vm.setActiva(!estado.activa)})
            Text(text = "activa")
        }
        if(estado.tipo == TipoAgencia.MAYORISTA) {
            Text(
                text = vm.getTextLabelReceptivo(),
                modifier = Modifier.clickable {
                    vm.setShowLayoutPickReceptivo(!estado.showLayoutPickReceptivo)
                })
        }
        if(estado.showLayoutPickReceptivo){ LayoutPickReceptivo() }
        Row(modifier = Modifier.align(alignment = Alignment.End)) {
            if(estado.estado == Estado.EDITAR) {
                Button(onClick = { vm.showDialogConfirmEliminar(true) },
                    modifier = Modifier.padding(end = 10.dp)) {
                    Text(text = "ELIMINAR")
                }
            }
            Button(onClick = { if(estado.name.isNotEmpty()){ vm.upsertAgencia() } }) {
                Text(text = vm.getLabelMainButton())
            }
        }
    }
}

@Composable
fun LayoutTipoAgencia(vm: AgenciasScreenVM = hiltViewModel()){
    val tipoAgenciaList = listOf(TipoAgencia.MAYORISTA,TipoAgencia.RECEPTIVO)
    Row(verticalAlignment = Alignment.CenterVertically) {
        tipoAgenciaList.forEach{tipoAgencia ->
            if(tipoAgenciaList.indexOf(tipoAgencia)==0){ Spacer(modifier = Modifier.width(10.dp)) }
            Checkbox(checked = (vm.estado.tipo == tipoAgencia), onCheckedChange = {
                vm.setTipo(tipoAgencia)
                if(tipoAgencia == TipoAgencia.RECEPTIVO){
                    vm.setReceptivo(Agencia.getEmptyAgencia())
                    vm.setShowLayoutPickReceptivo(false)
                }
            })
            Text(text = tipoAgencia.name, style = MaterialTheme.typography.bodySmall)
        }
    }    
}

@Composable
fun LayoutPickReceptivo(vm: AgenciasScreenVM = hiltViewModel()){
    val receptivos = vm.getAllAgencias(TipoAgencia.RECEPTIVO).collectAsState(initial = emptyList())
    LazyRow {
        item { 
            SuggestionChip(
                onClick = {
                    vm.setReceptivo(Agencia.getEmptyAgencia())
                    vm.setShowLayoutPickReceptivo(false)
                }, label = { Text(text = "NINGUNO") })
        }
        items(receptivos.value){agencia ->
            if(agencia.activa) {
                SuggestionChip(
                    onClick = {
                        vm.setReceptivo(agencia)
                        vm.setShowLayoutPickReceptivo(false)
                              },
                    label = { Text(text = agencia.name) })
            }
        }
    }
}

@Composable
fun LayoutAgenciasRegistradas(vm: AgenciasScreenVM = hiltViewModel()){
    val agenciasList = vm.getAllAgencias().collectAsState(initial = emptyList())
    LazyColumn {
        item{
            Text(text = "Agencias registradas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 10.dp))
        }
        items(agenciasList.value.sortedBy { it.name }){agencia ->
            AgenciaCard(agencia = agencia)
        }
    }
}

@Composable
fun AgenciaCard(agencia: Agencia, vm: AgenciasScreenVM = hiltViewModel()){
    ElevatedCard(
        onClick = { vm.setEditarMode(agencia) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp)) {
            Text(text = agencia.name, fontWeight = FontWeight.Bold)
            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = agencia.tipo.name, style = MaterialTheme.typography.bodySmall)
                Text(text = if(agencia.activa) "ACTIVA" else "NO ACTIVA", style = MaterialTheme.typography.bodySmall)
            }
            if(agencia.tipo == TipoAgencia.MAYORISTA) {
                Log.d("agencia","agencia(id= ${agencia.id}, name= ${agencia.name}, " +
                        "idReceptivo = ${agencia.idReceptivoAsociado}, tipo = ${agencia.tipo.name}, activa = ${agencia.activa})")
                val receptivo = vm.getAgencia(agencia.idReceptivoAsociado)
                Text(
                    text = "receptivo: " + receptivo.name.ifEmpty { "NINGUNO" },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


