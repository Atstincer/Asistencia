package com.aavv.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aavv.asistencia.ui.theme.AsistenciaTheme
import com.aavv.asistencia.views.HotelsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AsistenciaTheme {
                HotelsScreen()
                //AgenciasScreen()
            }
        }
    }
}