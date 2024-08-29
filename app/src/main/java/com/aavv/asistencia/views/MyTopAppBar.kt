package com.aavv.asistencia.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(drawerState: DrawerState? = null){
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = "Asistencia App", maxLines = 1, overflow = TextOverflow.Ellipsis) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            IconButton(
                onClick = {
                    if(drawerState != null){
                        coroutineScope.launch { drawerState.open() }
                    }
                }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "drawer")
            }
        })
}