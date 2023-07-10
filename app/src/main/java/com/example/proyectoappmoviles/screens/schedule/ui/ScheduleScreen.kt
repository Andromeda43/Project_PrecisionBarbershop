package com.example.proyectoappmoviles.screens.schedule.ui

import android.app.DatePickerDialog
import android.icu.lang.UCharacter.toUpperCase
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.resumedate.ResumeDateViewModel
import com.example.proyectoappmoviles.screens.schedule.ScheduleScreenViewModel
import com.example.proyectoappmoviles.screens.services.ServicesScreenViewModel
import com.example.proyectoappmoviles.screens.services.ui.ServicesScreen
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.alegreyaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont
import com.google.firebase.Timestamp
import java.util.Date

@Composable
fun ScheduleScreen(navController: NavController, viewModel: ScheduleScreenViewModel){
    viewModel.getDates()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        ScheduleScreenBodyContent(navController, viewModel)
    }
}

@Composable
fun ScheduleScreenBodyContent(navController: NavController, viewModel: ScheduleScreenViewModel) {
    val barberSelected = viewModel.barberSelect

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        TopBarCreationHomeSchedule(navController)
        ButtonSelectDate(viewModel)
        Spacer(modifier = Modifier.padding(7.dp))
        TextBarberName(barberSelected)

        ButtonsAreaDates(viewModel, navController){

        }
    }
}

@Composable
fun ButtonsAreaDates(viewModel: ScheduleScreenViewModel, navController: NavController, function: () -> Unit) {
    val scheduleTurns = viewModel.scheduleTurns
    val mCalendar = Calendar.getInstance()
    val barberSelected = viewModel.barberSelect

    //Variables de recordadorio de los picker
    val dateSelected: Timestamp by viewModel.mDate.observeAsState(
        initial = Timestamp(Date(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH))))

    Log.d("fecha: ", dateSelected.toDate().toString())
    val datesInDay = viewModel.filterByDate(dateSelected)

    Box(modifier = Modifier
        .fillMaxSize()){
        ImageBackgroundButtonsAreaSchedule()
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            TextShedulesAvalaible()
            Spacer(modifier = Modifier.padding(10.dp))
            var scheduleHour = 0
            while (scheduleHour < scheduleTurns.size){
                Row()
                     {
                    ButtonTurnOption(viewModel.turnAvabilable(datesInDay, scheduleHour, barberSelected!!),
                        scheduleHour,
                        scheduleTurns,
                        dateSelected,
                        navController,
                        viewModel
                    )

                    scheduleHour++
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                    ButtonTurnOption(viewModel.turnAvabilable(datesInDay, scheduleHour, barberSelected),
                        scheduleHour,
                        scheduleTurns,
                        dateSelected,
                        navController,
                        viewModel
                        )
                    scheduleHour++

                }
                Spacer(modifier = Modifier.padding(15.dp))
            }
        }
    }
}

@Composable
fun TextShedulesAvalaible() {
    Text(text = "TURNOS DISPONIBLES",
        fontFamily = lusitanaBoldFont,
        color = GoldenOpaque,
        fontSize = 24.sp,
        textAlign = TextAlign.Center)
}

@Composable
fun ButtonTurnOption(
    turnAvailable: Boolean,
    scheduleHour: Int,
    scheduleTurns: MutableList<String>,
    dateSelected: Timestamp,
    navController: NavController,
    viewModel: ScheduleScreenViewModel
){

    Button(
        onClick = {
            viewModel.postSchedule(viewModel.createSchedule(dateSelected, scheduleTurns[scheduleHour]))
            viewModel.setScheduleCreate(viewModel.createSchedule(dateSelected, scheduleTurns[scheduleHour]))
            navController.navigate(ProjectScreens.ResumeDateScreen.name)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        enabled = turnAvailable,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .size(width = 170.dp, height = 42.dp)
    ) {
        Text(
            text = "${scheduleTurns[scheduleHour]}:00",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }

}


@Composable
fun ImageBackgroundButtonsAreaSchedule(){
    Image(painter = painterResource(
        id = R.drawable.bgareabuttonsschedule),
        contentDescription = "Background",
        alpha = 0.6f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Composable
fun TextBarberName(barberSelected: Barber?) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = toUpperCase(barberSelected?.firstname) + " "+ toUpperCase(barberSelected?.lastname),
                fontFamily = lusitanaBoldFont,
                color = GoldenOpaque,
                fontSize = 24.sp,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(2.dp))
            Divider(color = WhiteBone,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 20.dp))
        }

    }
}

@Composable
fun ButtonSelectDate(viewModel: ScheduleScreenViewModel) {
    //Inicializamos las variables del contexto actual (JC) y el calendario
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    //Variables de recordadorio de los picker
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
    //Traemos el date desde el View Model
    val mDate: Timestamp by viewModel.mDate.observeAsState(initial = Timestamp(Date(mYear,mMonth,mDay)))

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.modifyDate(mYear,mMonth, mDayOfMonth)
        }, mYear, mMonth, mDay
    ).apply {
        // Fecha mínima
        datePicker.minDate = Calendar.getInstance().apply {
            time = Date()
        }.timeInMillis

        // Fecha máxima (dentro de 7 días)
        datePicker.maxDate = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DAY_OF_MONTH, 7)
        }.timeInMillis
    }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(8.dp))
        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = GrayDark,
            disabledContainerColor = Color(0xFF73777F)),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Text(
                text = "Seleccion una Fecha",
                fontFamily = lusitanaFont,
                color = WhiteBone,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Fecha Seleccionada: ${mDate.toDate().date}/${mDate.toDate().month + 1}/${mDate.toDate().year}",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 18.sp,
            textAlign = TextAlign.Center

        )
    }
}

@Composable
fun TopBarCreationHomeSchedule(navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor)
    ){
        //Imagen de Cabecera
        Image(painter = painterResource(id = R.drawable.bgtopbarservicesscreen),
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
                .clickable {
                    navController.navigate(ProjectScreens.ServicesScreen.name)
                    ServicesScreenViewModel.clearServicesAdded()
                }
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
        Text(text = "Selecciona Un Horario",
            fontSize = 20.sp,
            fontFamily = lusitanaFont,
            textAlign = TextAlign.Center, color = WhiteBone,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
