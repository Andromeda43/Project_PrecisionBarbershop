package com.example.proyectoappmoviles.screens.resumedate.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Service
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.barbers.BarberScreenViewModel
import com.example.proyectoappmoviles.screens.resumedate.ResumeDateViewModel
import com.example.proyectoappmoviles.screens.services.ServicesScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont
import com.google.firebase.Timestamp

@Composable
fun ResumeDateScreen(navController: NavController, viewModel: ResumeDateViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        ResumeDateScreenBodyContent(navController, viewModel)

    }
}

@Composable
fun ResumeDateScreenBodyContent(navController: NavController, viewModel: ResumeDateViewModel) {
    val scheduleCreated = viewModel.scheduleCreate

    Box(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.5f)
        .border(BorderStroke(1.dp, WhiteBone))
        .clip(RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextReserveSuccesfull()
            Spacer(modifier = Modifier.padding(8.dp))
            TextBarberInfo(scheduleCreated?.barberAttend)
            Spacer(modifier = Modifier.padding(3.dp))
            TextScheduleDate(scheduleCreated?.datetime)
            Spacer(modifier = Modifier.padding(3.dp))
            TextServicesAdded(scheduleCreated?.servicesInclude)
            Spacer(modifier = Modifier.padding(15.dp))
            ButtonForAcept(navController)


        }
    }
}

@Composable
fun ButtonForAcept(navController: NavController) {
    Button(onClick = {
        BarberScreenViewModel.barberSelect = null
        ServicesScreenViewModel.clearServicesAdded()
        navController.navigate(ProjectScreens.MenuScreen.name)
    }, colors = ButtonDefaults.buttonColors(
        containerColor = GoldenOpaque,
        disabledContainerColor = Color(0xFF73777F)
    ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Text(
            text = "Continuar",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextServicesAdded(servicesInclude: MutableList<Service?>?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Servicios Añadidos",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 10.dp)

        )
        if (servicesInclude?.isNotEmpty() == true){
            for (serviceInclude in servicesInclude){
                if (serviceInclude?.equals(null) == false){
                    Text(text = serviceInclude.name_service,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                } else{

                    Text(text = "No se Añadieron Servicios",
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }else{
            Text(text = "No se Añadieron Servicios",
                fontFamily = lusitanaFont,
                color = WhiteBone,
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

    }
}

@Composable
fun TextScheduleDate(datetime: Timestamp?) {
    val mYear = datetime?.toDate()?.year
    val mMonth = datetime?.toDate()?.month
    val mDay = datetime?.toDate()?.date

    Row() {
        Text(text = "Horario:",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 12.dp)
        )
        Text(text = "$mDay/$mMonth/$mYear",
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Composable
fun TextBarberInfo(barberAttend: Barber?) {
    Row() {
        Text(text = "Barbero:",
            fontFamily = lusitanaBoldFont,
            color = GoldenOpaque,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 12.dp)
        )
        Text(text = "${barberAttend?.firstname} ${barberAttend?.lastname}",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 1.dp)
        )
    }
}

@Composable
fun TextReserveSuccesfull(){
    Text(text = "Reserva Existosa!",
        fontFamily = lusitanaBoldFont,
        color = GoldenOpaque,
        fontSize = 25.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 5.dp)
        )
    Spacer(modifier = Modifier.padding(2.dp))
    Divider(color = GoldenOpaque,
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 70.dp))

}
