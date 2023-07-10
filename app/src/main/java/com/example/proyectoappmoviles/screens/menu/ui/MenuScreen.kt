
package com.example.proyectoappmoviles.screens.menu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.menu.MenuScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.alegreyaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont


@Composable
fun MenuScreen(navController: NavController, viewModel: MenuScreenViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        MenuScreenBodyContent(navController, viewModel)
    }
}

@Composable
fun MenuScreenBodyContent(navController: NavController, viewModel: MenuScreenViewModel) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        TopBarMenuScreen(viewModel)
        Spacer(modifier = Modifier.padding(top = 15.dp))
        RowImages(navController)
        Spacer(modifier = Modifier.padding(20.dp))
        WelcomeText()
        OptionsBox(navController, viewModel)
        Spacer(modifier = Modifier.padding(5.dp))

    }

}

@Composable
fun ButtonForLogout(navController: NavController, viewModel: MenuScreenViewModel) {
    Button(
        onClick = {
            viewModel.disconectUser()
            navController.navigate(ProjectScreens.LoginScreen.name)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GrayDark,
            disabledContainerColor = Color(0xFF73777F)
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 100.dp)
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Text(
            text = "Cerrar Sesion",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 14.sp
        )
    }
}

@Composable
fun WelcomeText(){
    Text(text = "BIENVENIDO",
        fontSize = 28.sp,
        fontFamily = lusitanaBoldFont,
        textAlign = TextAlign.Center,
        color = WhiteBone,
    )
    Divider(color = GoldenOpaque,
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 50.dp))
    Spacer(modifier = Modifier.padding(3.dp))

    Divider(color = GoldenOpaque,
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 20.dp))
}



@Composable
fun OptionsBox(navController: NavController, viewModel: MenuScreenViewModel){
    Box(modifier = Modifier
        .fillMaxHeight(1f)
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
        ){
        Image(painter = painterResource(R.drawable.menuoptionsbg),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
        )
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ButtonForReserveMenu(navController, ProjectScreens.BarberScreen.name, "Realiza Tu Reserva")
            Spacer(modifier = Modifier.padding(15.dp))
            ButtonForReserveMenu(navController, ProjectScreens.HistoryScreen.name, "Gestiona Tus Reservas")
            Spacer(modifier = Modifier.padding(bottom = 140.dp))

            ButtonForLogout(navController, viewModel)

        }
    }

}

@Composable
fun ButtonForReserveMenu(navController: NavController, route: String, textInput: String) {
    Button(
        onClick = {
            navController.navigate(route)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .height(50.dp)
    ) {
        Text(
            text = textInput,
            fontFamily = alegreyaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TopBarMenuScreen(viewModel: MenuScreenViewModel){
    val sedeRegistered: Sede? = viewModel.sedeSelect

    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(R.drawable.topbarmenu),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            //Tecto Centrado
            sedeRegistered?.namesede?.let {
                Text(text = it,
                    fontSize = 22.sp,
                    fontFamily = lusitanaBoldFont,
                    textAlign = TextAlign.Center,
                    color = WhiteBone,
                )
            }
            sedeRegistered?.direction?.let {
                Text(text = it,
                    fontSize = 16.sp,
                    fontFamily = lusitanaFont,
                    textAlign = TextAlign.Center,
                    color = WhiteBone,
                )
            }
        }
    }
}

@Composable
fun RowImages(navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.15f),
        contentAlignment = Alignment.Center
    ){
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            ComponentImageMaq()
            Spacer(modifier = Modifier.padding(25.dp))
            ComponentImageSedes(navController)
            Spacer(modifier = Modifier.padding(25.dp))
            ComponentImageInfo(navController)
        }
    }
}

@Composable
fun ComponentImageMaq() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.maquinadeafeitar),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.14f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(text = "Menu",
            fontSize = 18.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center,
            color = WhiteBone,
        )
    }
}

@Composable
fun ComponentImageSedes(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                navController.navigate(ProjectScreens.LocationScreen.name)
            }
        ) {
        Image(painter = painterResource(R.drawable.pinlocation),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.18f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(text = "Sedes",
            fontSize = 18.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center,
            color = WhiteBone,
        )
    }
}

@Composable
fun ComponentImageInfo(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            //navController.navigate(ProjectScreens.LocationScreen.name)
        }
    ) {
        Image(painter = painterResource(R.drawable.informacion),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.33f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(text = "Info",
            fontSize = 18.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center,
            color = WhiteBone,
        )
    }
}

