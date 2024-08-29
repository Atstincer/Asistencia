package com.aavv.asistencia.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.viewmodels.NewReservaVM

@Composable
fun NewReservaScreen(drawerState: DrawerState, vm: NewReservaVM = hiltViewModel()){

    val estado = vm.estado

    val agenciasList = vm.getAllAgencias().collectAsState(initial = emptyList())
    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(drawerState) }) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(all = 10.dp)
        ) {
            //Encabezado
            Text(text = "Nueva reserva", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(10.dp))

            //Referencia
            OutlinedTextField(
                value = estado.referencia,
                onValueChange = { vm.setReferencia(it) },
                label = { Text(text = "Referencia")})

            //Nombre cliente
            OutlinedTextField(
                value = estado.nombreCliente,
                onValueChange = { vm.setNombreCliente(it) },
                label = { Text(text = "Nombre cliente")},
                modifier = Modifier.padding(top = 10.dp))

            //Cant de pax
            Row (modifier = Modifier.padding(top = 10.dp)) {

                //Adultos
                OutlinedTextField(
                    value = estado.adultos,
                    onValueChange = { vm.setAdultos(it)},
                    label = { Text(text = "Ad")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.width(70.dp)
                )
                //textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)

                //Menores
                OutlinedTextField(
                    value = estado.menores,
                    onValueChange = { vm.setMenores(it) },
                    label = { Text(text = "Men")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(70.dp)
                )

                //Infantes
                OutlinedTextField(
                    value = estado.infantes,
                    onValueChange = { vm.setInfantes(it) },
                    label = { Text(text = "Inf")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(70.dp)
                )
            }

            //Agencia
            OutlinedTextField(modifier = Modifier.padding(top = 10.dp),
                value = estado.agencia,
                onValueChange = {
                    vm.setAgencia(it)
                    vm.setIdAgenciaSelected(0)
                    vm.setShowAgenciasToPick(true)
                                },
                label = { Text(text = "agencia") } )

            //Mostrar agencias para seleccionar
            if(estado.showAgenciasToPick && estado.agencia.isNotEmpty()){
                LazyRow {
                    items(agenciasList.value.filter {
                        it.name.lowercase().contains(estado.agencia.lowercase())
                    }){
                        SuggestionChip(
                            onClick = {
                                vm.setAgencia(it.name)
                                vm.setIdAgenciaSelected(it.id)
                                vm.setShowAgenciasToPick(false)
                                      },
                            label = { Text(text = it.name) },
                            modifier = Modifier.padding(end = 2.dp))
                    }
                }
            }

            //Celular - Whatsapp
            OutlinedTextField(modifier = Modifier.padding(top = 10.dp),
                value = estado.cell,
                onValueChange = { vm.setCell(it) },
                label = { Text(text = "Telefono / cell")} )

            //Mail
            OutlinedTextField(modifier = Modifier.padding(top = 10.dp),
                value = estado.mail,
                onValueChange = { vm.setMail(it) },
                label = { Text(text = "Mail")} )

            //Estancias
            if(estado.estado == Estado.EDITAR){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Estancias asociadas", style = MaterialTheme.typography.headlineMedium)
                    Icon(Icons.Default.Add, contentDescription = "Adicionar estancia")
                }
            }

            Button(
                onClick = {
                    if(vm.isValidReserva()) {
                        vm.upsertReserva()
                        vm.setEditarMode()
                    }
                          },
                modifier = Modifier.padding(top = 10.dp)) {
                Text(text = if(estado.estado == Estado.NEW) "Registrar" else "Editar")
            }

        }
    }
}


@Composable
fun LayoutAddNuevaEstancia(){
    var hotel by remember {
        mutableStateOf("")
    }
    Column (modifier = Modifier.fillMaxWidth()) {
        TextField(value = hotel, onValueChange = {hotel = it},
            label = { Text(text = "Hotel")})
        Row{
            Text(text = "Fecha in")
            Text(text = "Fecha out", modifier = Modifier.padding(start = 10.dp))
        }
    }
}
