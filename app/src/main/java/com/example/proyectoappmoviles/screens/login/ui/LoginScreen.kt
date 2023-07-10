package com.example.proyectoappmoviles.screens.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.ViewModel
import com.example.proyectoappmoviles.R
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.login.LoginScreenViewModel
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.alegreyaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ImageBackgroundLoginScreen()
        LoginComponent(viewModel, navController)
    }
}

@Composable
fun LoginComponent(viewModel: LoginScreenViewModel, navController: NavController){
    //Variables Mutables para Componentes
    val email: String by viewModel.email.observeAsState(initial = "") //Email Mutable que se escribe
    val password: String by viewModel.password.observeAsState(initial = "") //Password Mutable que se escribe
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false) //Cambiar a false para poder habilitarlo
    val messageAuth: String by viewModel.messageAuth.observeAsState(initial = "") //Mensaje para Inicio de sesion
    val context = LocalContext.current // El contexto Actual para mostrar los mensajes emegerntes

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        TextTitleLoginScreen()

        Spacer(modifier = Modifier.padding(bottom = 20.dp))

        EmailField(email) { viewModel.onLoginChange(it, password) }

        Spacer(modifier = Modifier.padding(15.dp))

        PasswordField(password, viewModel) { viewModel.onLoginChange(email, it) }

        Spacer(modifier = Modifier.padding(22.dp))

        BotonLoguearse(loginEnable) {
            //Esto es lo que hace todo el proceso del login
           viewModel.signInWithEmailAndPassword(email, password){
                navController.navigate(ProjectScreens.LocationScreen.name) }
           Toast.makeText(context,messageAuth, Toast.LENGTH_SHORT).show()

            //navController.navigate(ProjectScreens.LocationScreen.name)
        }

        Spacer(modifier = Modifier.padding(5.dp))
        CreateAccountText(navController)
    }
}

@Composable
fun TextTitleLoginScreen(){
    Text(text = "Precision",
        fontFamily = lusitanaBoldFont,
        color = WhiteBone,
        fontSize = 48.sp
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(text = "BarberShop",
        fontFamily = lusitanaBoldFont,
        color = GoldenOpaque,
        fontSize = 48.sp
    )
}



@Composable
fun ImageBackgroundLoginScreen(){
    Image(painter = painterResource(
        id = R.drawable.backgroundimage),
        contentDescription = "Background",
        alpha = 0.2f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit){
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = "Email", fontFamily = lusitanaFont) },
        placeholder = { Text(text = "Ingresa Tu Email", fontFamily = lusitanaFont) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = GrayDark.copy(alpha = 0.9f),
            textColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            placeholderColor = WhiteBone
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
fun BotonLoguearse(loginEnable: Boolean, function: () -> Unit){
    Button(
        onClick = {
            function()
                  },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
            ),
        enabled = loginEnable,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "Iniciar Sesion",
            fontFamily = alegreyaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CreateAccountText(navController: NavController){
    Text(text = "Registrate Aqui",
        fontFamily = alegreyaBoldFont,
        color = WhiteBone,
        fontSize = 22.sp,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            navController.navigate(ProjectScreens.RegisterScreen.name)
            }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, viewModel: LoginScreenViewModel, onTextFieldChanged: (String) -> Unit){
    //Variables
    val passwordVisible: Boolean by viewModel.passwordVisible.observeAsState(initial = false)

    //Para Cambio Visual
    val visualTransformation = if (passwordVisible)
        VisualTransformation.None
    else PasswordVisualTransformation()

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = "Contraseña", fontFamily = lusitanaFont) },
        placeholder = { Text(text = "Ingresa Tu Contraseña", fontFamily = lusitanaFont) },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = GrayDark.copy(alpha = 0.9f),
            textColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            placeholderColor = WhiteBone
        ),
        trailingIcon = {
            if(password.isNotEmpty()){
                PasswordVisibleIcon(passwordVisible, viewModel)
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: Boolean, viewModel: LoginScreenViewModel) {
    val image =
        if (passwordVisible)
            Icons.Default.VisibilityOff
        else Icons.Default.Visibility
    IconButton(onClick = { viewModel.changePasswordVisible(passwordVisible) }) {
        Icon(
            imageVector = image,
            contentDescription = "",
            tint = WhiteBone
        )
    }
}
