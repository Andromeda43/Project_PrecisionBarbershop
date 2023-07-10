package com.example.proyectoappmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.proyectoappmoviles.navigation.AppNavigation
import com.example.proyectoappmoviles.theme.ProyectoAPPMovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoAPPMovilesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProjectApp()
                }
            }
        }
    }
}

@Composable
fun ProjectApp(){
    Surface(modifier = Modifier
        .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavigation()
    }
}