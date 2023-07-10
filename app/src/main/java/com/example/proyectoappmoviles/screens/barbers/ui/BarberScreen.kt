package com.example.proyectoappmoviles.screens.barbers.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.barbers.BarberScreenViewModel
import com.example.proyectoappmoviles.screens.location.ui.CardComponentSede
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont

@Composable
fun BarberScreen(navController: NavController, viewModel: BarberScreenViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        BarberScreenBodyContent(navController, viewModel)
    }
}

@Composable
fun BarberScreenBodyContent(navController: NavController, viewModel: BarberScreenViewModel) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        //Cabecera
        TopBarCreationHomeBarber(navController)
        Spacer(modifier = Modifier.padding(5.dp))
        //Creacion de los barberos
        LazyColumnForBarbers(navController, viewModel)
    }
}

@Composable
fun LazyColumnForBarbers(navController: NavController, viewModel: BarberScreenViewModel) {
    val barberList: List<Barber> = viewModel.sedeSelect?.barbersList as List<Barber>

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(){
            items(barberList){ item ->
                CardComponentBarber(item){
                    viewModel.setBarberSelect(item)
                    navController.navigate(ProjectScreens.ServicesScreen.name)
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CardComponentBarber(barberItem: Barber, function: () -> Unit) {
    Card(border = BorderStroke(1.dp, WhiteBone),
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = BackGroundColor),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ){
        Row(
            modifier = Modifier.clickable { function() },
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Box(modifier = Modifier.fillMaxWidth(0.44f)){
                Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = barberItem.imgref,
                        contentDescription = "Imagen Barber icon",
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(0.65f)
                    )

                    Text(text = barberItem.firstname,
                        fontSize = 20.sp,
                        fontFamily = lusitanaBoldFont,
                        textAlign = TextAlign.Center,
                        color = GoldenOpaque,
                    )
                    Spacer(modifier = Modifier.padding(1.dp))
                    Text(text = barberItem.lastname,
                        fontSize = 20.sp,
                        fontFamily = lusitanaBoldFont,
                        textAlign = TextAlign.Center,
                        color = GoldenOpaque,
                    )
                }
            }

            Box(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(0.8f)){
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(text = "${barberItem.experience} años",
                        fontSize = 20.sp,
                        fontFamily = lusitanaBoldFont,
                        textAlign = TextAlign.Center,
                        color = WhiteBone,
                    )
                    Text(text = "Puntaje:",
                        fontSize = 20.sp,
                        fontFamily = lusitanaBoldFont,
                        textAlign = TextAlign.Center,
                        color = GoldenOpaque
                    )
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        for(i in 1..barberItem.rating){
                            Image(painter = painterResource(
                                id = R.drawable.star),
                                contentDescription = "Star Icon",
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.CenterVertically),
                                contentScale = ContentScale.Inside
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Box(modifier = Modifier.fillMaxWidth(0.8f)){
                Image(painter = painterResource(
                    id = R.drawable.nextarrow),
                    contentDescription = "Star Icon",
                    modifier = Modifier
                        .size(32.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun TopBarCreationHomeBarber(navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor)
    ){
        //Imagen de Cabecera
        Image(painter = painterResource(id = R.drawable.bgbarberscreen),
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
                .clickable { navController.navigate(ProjectScreens.MenuScreen.name) }
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
        Text(text = "Elige tu Barbero",
            fontSize = 30.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center, color = WhiteBone,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
