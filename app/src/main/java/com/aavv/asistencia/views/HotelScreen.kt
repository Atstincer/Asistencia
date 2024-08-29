@file:OptIn(ExperimentalMaterial3Api::class)

package com.aavv.asistencia.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.aavv.asistencia.constantes.Destinos
import com.aavv.asistencia.constantes.Estado
import com.aavv.asistencia.models.Hotel
import com.aavv.asistencia.viewmodels.HotelsScreenVM


@Composable
fun HotelsScreen(drawerState: DrawerState,vm: HotelsScreenVM = hiltViewModel()){
    val estado = vm.estado
    BackHandler (enabled = (estado.estado == Estado.EDITAR)) { vm.setRegularMode() }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = { MyTopAppBar(drawerState) }) {innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(all = 10.dp)) {
            LayoutAddNewHotel()
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp))
            LayoutHotelesRegistrados()
        }
    }

    if(vm.estado.mostrarDialogConfirmEliminar){
        DialogConfirmEliminar(
            title = "Confirmar",
            body = "Seguro que desea eliminar el hotel '${estado.hotelSelected.name}'",
            onDismiss = { vm.mostrarDialogConfirmEliminar(false) }) {
            vm.deleteHotel()
        }
    }
}

@Composable
fun LayoutHotelesRegistrados(vm: HotelsScreenVM = hiltViewModel()){
    val hotelList = vm.getAllHotels().collectAsState(initial = emptyList())
    LazyColumn(modifier=Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(6.dp)
    ) {
        item {
            Text(text = "Hoteles registrados",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 10.dp))
        }
        items(hotelList.value.sortedBy { it.name }){hotel->
            HotelCard(hotel)
        }
    }
}

@Composable
fun LayoutAddNewHotel(vm:HotelsScreenVM = hiltViewModel()){
    val estado = vm.estado
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = estado.name,
            onValueChange = {vm.setName(it)},
            modifier=Modifier.fillMaxWidth(),
            label = { Text(text = "Hotel") },
            maxLines = 1,
            trailingIcon = { IconButton(onClick = { vm.setName("") }) {
                Icon(Icons.Filled.Clear, contentDescription = "Borrar campo")
            } })
        Text(text = "destino: ${estado.destino.name}",
            Modifier
                .padding(start = 10.dp, top = 5.dp)
                .clickable { vm.mostrarDestinos(!estado.mostrarDestinos) })
        if(estado.mostrarDestinos) {
            LazyRow {
                items(Destinos.entries) { destino ->
                    if (destino.ordinal != 0) {
                        Spacer(modifier = Modifier.width(3.dp))
                    }
                    SuggestionChip(
                        onClick = {
                            vm.setDestino(destino)
                            vm.mostrarDestinos(false)
                            },
                        label = { Text(text = destino.name) }
                    )
                }
            }
        }
        Row(modifier = Modifier.align(alignment = Alignment.End)) {
            if(estado.estado == Estado.EDITAR){
                Button(onClick = { vm.mostrarDialogConfirmEliminar(true) },
                    modifier = Modifier.padding(end = 10.dp)) {
                    Text(text = "Eliminar")
                }
            }
            Button(onClick = {
                if (estado.name.isNotEmpty()) {
                    vm.upsertHotel()
                }
            }) {
                Text(text = if(estado.estado == Estado.NEW) "Añadir" else "Editar")
            }
        }

    }
}

@Composable
fun HotelCard(hotel:Hotel, vm:HotelsScreenVM = hiltViewModel()){
    ElevatedCard(onClick = { vm.setEditarMode(hotel) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(modifier = Modifier.padding(start = 10.dp, top = 3.dp, bottom = 3.dp, end = 3.dp)) {
            Text(text = hotel.name, fontWeight = FontWeight.Bold)
            Text(text = hotel.destino.name, style = MaterialTheme.typography.bodySmall)
        }
    }
}