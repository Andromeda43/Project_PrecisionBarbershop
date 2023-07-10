package com.example.proyectoappmoviles.screens.services.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Service
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.services.ServicesScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.RedCarmesi
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.lusitanaFont

@Composable
fun ServicesScreen(navController: NavController, viewModel: ServicesScreenViewModel){
    viewModel.getServicesFromDB()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        ServicesScreenBodyContent(navController, viewModel)
    }

}

@Composable
fun ServicesScreenBodyContent(navController: NavController, viewModel: ServicesScreenViewModel) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        TopBarCreationHomeServices(navController)
        Spacer(modifier = Modifier.padding(5.dp))

        LazyColumnForServices(viewModel)

        Spacer(modifier = Modifier.padding(1.dp))

        ButtonForContinue(){
            navController.navigate(ProjectScreens.ScheduleScreen.name)
        }

    }
}

@Composable
fun ButtonForContinue(function: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(1f)
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = {
            function()
        },  modifier = Modifier.fillMaxWidth(0.42f).fillMaxHeight(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = RedCarmesi),
            shape = RoundedCornerShape(8),
        ) {
            Text(text = "Continuar",
                fontSize = 19.sp,
                fontFamily = lusitanaFont,
                textAlign = TextAlign.Center,
                color = WhiteBone
            )
        }
    }

}

@Composable
fun LazyColumnForServices(viewModel: ServicesScreenViewModel) {
    val servicesList:List<Service> by viewModel.servicesRegistered.observeAsState(initial = mutableListOf())
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.9f)
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(){
            items(servicesList){ item ->
                CardComponentService(item, viewModel)
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

    }

}

@Composable
fun CardComponentService(serviceItem: Service, viewModel: ServicesScreenViewModel) {
    Card(border = BorderStroke(1.dp, WhiteBone),
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = BackGroundColor),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ){
        Column {
            Text(text = serviceItem.name_service,
                fontSize = 18.sp,
                fontFamily = lusitanaFont,
                textAlign = TextAlign.Start,
                color = WhiteBone,
                modifier = Modifier.padding(8.dp)
            )
            Divider(color = WhiteBone,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 10.dp))
            Row {
                Box(modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.2f)) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        //Imagen Cargada desde Firebase
                        AsyncImage(
                            model = serviceItem.imgref,
                            contentDescription = "Imagen Service icon",
                            alignment = Alignment.CenterStart,
                            colorFilter = ColorFilter.tint(GoldenOpaque),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .fillMaxHeight(0.8f)
                                .padding(8.dp)
                        )
                    }
                }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Box(modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(0.5f)) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$ ${serviceItem.price}",
                                fontSize = 22.sp,
                                fontFamily = lusitanaFont,
                                textAlign = TextAlign.Center,
                                color = WhiteBone,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                Box(modifier = Modifier
                    .padding(start = 14.dp, top = 14.dp)
                    .fillMaxWidth(0.9f)){
                    Column (verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End
                    ){
                        ButtonAdding(serviceItem, viewModel,
                            Modifier
                                .align(Alignment.End)
                                .fillMaxWidth().height(30.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonAdding(serviceItem: Service, viewModel: ServicesScreenViewModel, modifier: Modifier){
    var textButton by remember { mutableStateOf("Añadir") }
    if (viewModel.isAdded(serviceItem)){
        textButton = "Eliminar"
    }else textButton = "Añadir"

    Button(onClick = {
        viewModel.updateServicesAddes(serviceItem)
        if (viewModel.isAdded(serviceItem)){
            textButton = "Eliminar"
        }else textButton = "Añadir"

    },  modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = GoldenOpaque),
        shape = RoundedCornerShape(8),
    ) {
        Text(text = textButton,
            fontSize = 12.sp,
            fontFamily = lusitanaFont,
            color = WhiteBone
        )
    }
}

@Composable
fun TopBarCreationHomeServices(navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor)
    ){
        //Imagen de Cabecera
        Image(painter = painterResource(id = R.drawable.bgtopscreenservice),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize())

        //Flechita de regreso
        Image(painter = painterResource(id = R.drawable.backarrow),
            contentDescription = "Devolverse",
            modifier = Modifier
                .padding(vertical = 25.dp, horizontal = 18.dp)
                .size(35.dp)
                .align(Alignment.CenterStart)
                .clickable { navController.navigate(ProjectScreens.BarberScreen.name) }
        )

        //Icono Home
        Image(painter = painterResource(id = R.drawable.homeicon),
            contentDescription = "Home",
            modifier = Modifier
                .padding(vertical = 25.dp, horizontal = 10.dp)
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .clickable { navController.navigate(route = ProjectScreens.MenuScreen.name) }
        )
        //Texto Centrado
        Text(text = "Servicios Adicionales",
            fontSize = 25.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center, color = WhiteBone,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
