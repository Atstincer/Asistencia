package com.aavv.asistencia.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)){
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent{ route ->
                    coroutineScope.launch { drawerState.close() }
                    navController.navigate(route)
                }
            }
        }) {
        NavHost(navController = navController, startDestination = Routes.NuevaReserva.name){
            composable(Routes.Hoteles.name) {
                HotelsScreen(drawerState)
            }
            composable(Routes.Agencias.name) {
                AgenciasScreen(drawerState)
            }
            composable(Routes.NuevaReserva.name) { 
                NewReservaScreen(drawerState = drawerState)
            }
            composable(Routes.Reservas.name) {
                ReservasScreen(drawerState)
            }
        }
    }
}

enum class Routes(value: String){
    NuevaReserva("nueva_reserva"),
    Reservas("reservas"),
    Hoteles("hoteles"),
    Agencias("agencias")
}

private data class DrawerMenuItem(val icon: ImageVector, val title: String, val route: String)

private val listaMenuItems = listOf(
    DrawerMenuItem(Icons.Default.AddCircle,"Nueva Reserva", Routes.NuevaReserva.name),
    DrawerMenuItem(Icons.Default.AddCircle,"Reservas",Routes.Reservas.name),
    DrawerMenuItem(Icons.Default.Home, "Hoteles", Routes.Hoteles.name),
    DrawerMenuItem(Icons.Default.AccountCircle, "Agencias", Routes.Agencias.name)
)

@Composable
private fun DrawerContent(
    onItemClick: (String)->Unit
){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Asistencia App", modifier = Modifier.padding(top = 20.dp, start = 15.dp))
        Spacer(modifier = Modifier.height(12.dp))
        listaMenuItems.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = { onItemClick(it.route) })
        }
    }
}