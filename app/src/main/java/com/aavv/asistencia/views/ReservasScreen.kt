package com.aavv.asistencia.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aavv.asistencia.viewmodels.ReservasScreenVM

@Composable
fun ReservasScreen(drawerState: DrawerState ,vm: ReservasScreenVM = hiltViewModel()){
    Scaffold(topBar = { MyTopAppBar(drawerState)}) { innerPading ->
        val reservasList = vm.getAllReservas().collectAsState(initial = emptyList())

        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPading).padding(all = 10.dp)) {
            item {
                Text(text = "Reservas registradas", style = MaterialTheme.typography.headlineMedium)
            }
            itemsIndexed(reservasList.value){ index,reserva ->
                if(index > 0) { HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(top = 10.dp, bottom = 10.dp)) }
                Text(text = "id= ${reserva.id}")
                Text(text = "nombre= ${reserva.nombreCliente}    agencia= ${reserva.idAgencia}")
                Text(text = "ref= ${reserva.referencia}   ad= ${reserva.adultos}  men= ${reserva.menores}   " +
                        "inf= ${reserva.infantes}")
            }
        }
    }
}