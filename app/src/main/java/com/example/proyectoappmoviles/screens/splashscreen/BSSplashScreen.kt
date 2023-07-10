package com.example.proyectoappmoviles.screens.splashscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun BSSplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(1)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ProjectScreens.LoginScreen.name)
        }else{
            navController.navigate(ProjectScreens.LocationScreen.name)

        }
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageBackgroundSplashScreen()
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            TextTitle()
            Spacer(modifier = Modifier.padding(20.dp))
            CircleLoading()
        }
    }
}

@Composable
fun ImageBackgroundSplashScreen(){
    Image(painter = painterResource(
        id = R.drawable.backgroundimage),
        contentDescription = "Background",
        alpha = 0.2f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    )
}

@Composable
fun TextTitle(){
    Text(text = "Precision",
        fontFamily = lusitanaBoldFont,
        color = WhiteBone,
        fontSize = 48.sp
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(text = "BarberShop",
        fontFamily = lusitanaBoldFont,
        color = WhiteBone,
        fontSize = 48.sp
    )
}

@Composable
fun CircleLoading(){
    CircularProgressIndicator(
        modifier = Modifier.size(44.dp),
        color = GoldenOpaque
    )
}