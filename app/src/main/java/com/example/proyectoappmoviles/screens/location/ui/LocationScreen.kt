package com.example.proyectoappmoviles.screens.location.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.location.LocationScreenViewModel
import com.example.proyectoappmoviles.screens.location.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.proyectoappmoviles.screens.menu.MenuScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.alegreyaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Composable
fun LocationScreen(navController: NavController, viewModel: LocationScreenViewModel){
    viewModel.getSedes()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        LocationScreenBodyContent(navController, viewModel)


    }

}

@Composable
fun LocationScreenBodyContent(navController: NavController, viewModel: LocationScreenViewModel) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        TopBarSucursal()
        //Area donde va a ir el Lazy Column de las sucurales
        //BotonTemporalPaPruebasXd(navController, viewModel)
        LazyColumnSedes(navController, viewModel)
        //Texto y Linea
        TextLineDivide()

        //Area de Google Map
        GoogleMapDrawer(viewModel)
    }

}

@Composable
fun LazyColumnSedes(navController: NavController, viewModel: LocationScreenViewModel) {
    val sedesList: List<Sede> by viewModel.sedesRegistered.observeAsState(initial = mutableListOf()) //Email Mutable que se escribe
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.3f)
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(){
            items(sedesList){ item ->
                CardComponentSede(item){
                    viewModel.setSedeSelect(item)
                    navController.navigate(ProjectScreens.MenuScreen.name)
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

    }
}

@Composable
fun CardComponentSede(sedeObj: Sede, function: () -> Unit){
    Card(border = BorderStroke(1.dp, WhiteBone),
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = BackGroundColor),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.clickable { function() }
        ){
            Image(painter = painterResource(
                id = R.drawable.pinlocation),
                contentDescription = "Location",
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxHeight(0.1f)
                    .fillMaxWidth(0.13f),
                contentScale = ContentScale.Fit
                )
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start){
                Text(text = sedeObj.namesede,
                    fontSize = 18.sp,
                    fontFamily = lusitanaBoldFont,
                    textAlign = TextAlign.Center,
                    color = WhiteBone,
                )
                Text(text = sedeObj.direction,
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
fun TextLineDivide() {
    Text(text = "Visualiza las sucursales a tu alrededor",
        fontSize = 18.sp,
        fontFamily = alegreyaBoldFont,
        textAlign = TextAlign.Center,
        color = WhiteBone,
        modifier = Modifier.padding(3.dp))
    Divider(color = WhiteBone,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSucursal(){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.1f)
        .background(BackGroundColor)
    ){
        Image(painter = painterResource(R.drawable.topbarsucursal),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f)
        )
        //Tecto Centrado
        Text(text = "Selecciona Una Sucursal",
            fontSize = 26.sp,
            fontFamily = lusitanaBoldFont,
            textAlign = TextAlign.Center,
            color = WhiteBone,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GoogleMapDrawer(viewModel: LocationScreenViewModel){
    GetLocationPermission(viewModel)

    val locationPermissionGranted: Boolean = viewModel.locationPermissionGranted.isInitialized
    val sedesList: List<Sede> by viewModel.sedesRegistered.observeAsState(initial = mutableListOf()) //Email Mutable que se escribe

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(4.090380715464097, -76.19722508690373), 12f)
    }
    if(locationPermissionGranted){
        GoogleMap(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),//Recorta los bordes del mapa
            cameraPositionState = cameraPositionState,
            properties  = MapProperties(isMyLocationEnabled = true)
        ){
            for (sede in sedesList){
                Marker(state = rememberMarkerState(position = LatLng(sede.mapdir.latitude, sede.mapdir.longitude)),
                    title = sede.namesede,
                    snippet = sede.direction)
            }
        }
    }else{
        Text(text = "Se necesitan permisos")
    }
}

@Composable
fun GetLocationPermission(viewModel: LocationScreenViewModel) {
    val mContext = LocalContext.current

    if (ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        == PackageManager.PERMISSION_GRANTED
    ) {
        viewModel.changePermissionValue()
    } else {
        ActivityCompat.requestPermissions(
            mContext as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }
}
