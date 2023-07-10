package com.example.proyectoappmoviles.screens.history.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Schedules
import com.example.proyectoappmoviles.models.Service
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.history.HistoryScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont
import com.google.firebase.Timestamp

@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryScreenViewModel){
    viewModel.getSchedules()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        HistoryScreenBodyContent(navController, viewModel)
    }
}

@Composable
fun HistoryScreenBodyContent(navController: NavController, viewModel: HistoryScreenViewModel) {
    val histortyList: List<Schedules> by viewModel.schedulesRegistered.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        TopBarCreationHistoryScreen(navController)
        Spacer(modifier = Modifier.padding(5.dp))
        TextHistoy()
        LazyColumnForHistory(histortyList)
    }
}

@Composable
fun LazyColumnForHistory(histortyList: List<Schedules>) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
    ){
        LazyColumn(){
            items(histortyList){ item ->
                CardComponentHistory(item)
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CardComponentHistory(item: Schedules) {
    Card(border = BorderStroke(1.dp, WhiteBone),
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = BackGroundColor),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Reserva",
                fontFamily = lusitanaBoldFont,
                color = GoldenOpaque,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            )
            Row() {
                Column() {
                    TextHistoryBarber(item.barberAttend)
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextHistoryDate(item.datetime)
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextHistoryTime(item.datetime)

                }
                Spacer(modifier = Modifier.fillMaxWidth(0.25f))

                Column {
                    TextServicesAddedHistory(item.servicesInclude)
                }
            }
            TextFinalPrice(item.servicesInclude)
        }
    }
}

@Composable
fun TextHistoryBarber(barberAttend: Barber?) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = "Barbero:",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(text = "${barberAttend?.firstname} ${barberAttend?.lastname}",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun TextFinalPrice(servicesInclude: MutableList<Service?>) {
    var price = 15000
    if (servicesInclude.isNotEmpty()){
        for (serviceInclude in servicesInclude){
            if (serviceInclude?.equals(null) == false){
                price = (price + serviceInclude.price).toInt()
            }
        }
    }
    Text(text = "Precio Total: $price",
        fontFamily = lusitanaBoldFont,
        color = GoldenOpaque,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(5.dp)
        )
}

@Composable
fun TextServicesAddedHistory(servicesInclude: MutableList<Service?>) {
    Column {
        Text(text = "Servicios Añadidos",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 15.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(end = 10.dp)
        )
        if (servicesInclude.isNotEmpty()){
            for (serviceInclude in servicesInclude){
                if (serviceInclude?.equals(null) == false){
                    Text(text = serviceInclude.name_service,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 13.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }else{
                    Text(text = "No se Añadieron Servicios",
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 13.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        }else{
            Text(text = "No se Añadieron Servicios",
                fontFamily = lusitanaFont,
                color = WhiteBone,
                fontSize = 13.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

    }
}

@Composable
fun TextHistoryDate(datetime: Timestamp?) {
    val mYear = datetime?.toDate()?.year
    val mMonth = datetime?.toDate()?.month
    val mDay = datetime?.toDate()?.date

    Row() {
        Text(text = "Fecha: $mDay/$mMonth/$mYear",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun TextHistoryTime(datetime: Timestamp?) {
    val mHour = datetime?.toDate()?.hours
    Row() {
        Text(text = "Horario: $mHour:00",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun TextHistoy() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "HISTORIAL",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Divider(color = GoldenOpaque,
            thickness = 2.dp,
            modifier = Modifier.padding(horizontal = 50.dp))
        Spacer(modifier = Modifier.padding(3.dp))
        Divider(color = GoldenOpaque,
            thickness = 2.dp,
            modifier = Modifier.padding(horizontal = 25.dp))
    }
}

@Composable
fun TopBarCreationHistoryScreen(navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor)
    ){
        //Imagen de Cabecera
        Image(painter = painterResource(id = R.drawable.bghistoryscreentopbar),
            contentDescription = "",
            contentScale = ContentScale.Crop,
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
        Text(text = "Tus Reservas",
            fontSize = 26.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center, color = WhiteBone,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
