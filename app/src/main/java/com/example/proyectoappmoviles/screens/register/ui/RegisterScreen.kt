package com.example.proyectoappmoviles.screens.register.ui

import android.text.BoringLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoappmoviles.navigation.ProjectScreens
import com.example.proyectoappmoviles.screens.login.LoginScreenViewModel
import com.example.proyectoappmoviles.screens.login.ui.ImageBackgroundLoginScreen
import com.example.proyectoappmoviles.screens.login.ui.LoginComponent
import com.example.proyectoappmoviles.screens.login.ui.TextTitleLoginScreen
import com.example.proyectoappmoviles.screens.register.RegisterScreenViewModel
import com.example.proyectoappmoviles.theme.BackGroundColor
import com.example.proyectoappmoviles.theme.GoldenOpaque
import com.example.proyectoappmoviles.theme.GrayDark
import com.example.proyectoappmoviles.theme.WhiteBone
import com.example.proyectoappmoviles.theme.alegreyaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaBoldFont
import com.example.proyectoappmoviles.theme.lusitanaFont

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterScreenViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ){
        BodyContentRegisterScreen(navController, viewModel)
    }
}

@Composable
fun BodyContentRegisterScreen(navController: NavController, viewModel: RegisterScreenViewModel) {
    //Variables traidas desde el ViewModel
    val nameUser: String by viewModel.nameUser.observeAsState(initial = "")//Nombre mutable que se escribe
    val lastnameUser: String by viewModel.lastnameUser.observeAsState(initial = "")//Apellido mutable que se escribe
    val email: String by viewModel.email.observeAsState(initial = "") //Email Mutable que se escribe
    val password: String by viewModel.password.observeAsState(initial = "") //Password Mutable que se escribe
    val confirmPassword: String by viewModel.confirmPassword.observeAsState(initial = "") //ConfirmPassword Mutable que se escribe
    val phoneNumberUser: String by viewModel.phoneNumber.observeAsState(initial = "")

    //Variables para poder activar o desactivar el password
    val passwordVisible1: Boolean by viewModel.passwordVisible1.observeAsState(initial = false) //ConfirmPassword Mutable que se escribe
    val passwordVisible2: Boolean by viewModel.passwordVisible2.observeAsState(initial = false) //ConfirmPassword Mutable que se escribe

    //Habilitador del Login
    val registerEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)//Habilita el boton del registro

    //Contexto de la aplicacion para mensajes emergentes
    val context = LocalContext.current // El contexto Actual

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        TextTitleRegisterScreen()
        Spacer(modifier = Modifier.padding(20.dp))//Spacio

        TextFieldCommonRegister(nameUser, "Nombre", "Ingresa tu Nombre"){
            viewModel.onTextFieldChange(it, lastnameUser, email, phoneNumberUser, password, confirmPassword)
        }
        Spacer(modifier = Modifier.padding(15.dp)) // Spacio

        TextFieldCommonRegister(lastnameUser, "Apellido", "Ingresa tu Apellido"){
            viewModel.onTextFieldChange(nameUser, it, email, phoneNumberUser, password, confirmPassword)
        }
        Spacer(modifier = Modifier.padding(15.dp))// Spacio

        TextFieldCommonRegister(email, "Email", "Ingresa tu Email"){
            viewModel.onTextFieldChange(nameUser, lastnameUser, it, phoneNumberUser, password, confirmPassword)
        }
        Spacer(modifier = Modifier.padding(15.dp)) //Spacio

        TextFieldNumberRegister(phoneNumberUser, "Telefono", "Ingresa tu Numero de Telefono"){
            viewModel.onTextFieldChange(nameUser, lastnameUser, email, it, password, confirmPassword)
        }
        Spacer(modifier = Modifier.padding(15.dp)) //Spacio

        TextPasswordCommonField(
            atributeRequired = password,
            passwordVisible = passwordVisible1,
            labelText = "Contraseña",
            placeholderText = "Ingresa Tu Contraseña",
            funShowPassword = { viewModel.changePasswordVisible1(passwordVisible1) },
            onTextFieldChanged = {
                viewModel.onTextFieldChange(nameUser, lastnameUser, email, phoneNumberUser, it, confirmPassword)
            }
        )
        Spacer(modifier = Modifier.padding(15.dp)) //Spacio

        TextPasswordCommonField(
            atributeRequired = confirmPassword,
            passwordVisible = passwordVisible2,
            labelText = "Confirmación",
            placeholderText = "Confirma Tu Contraseña",
            funShowPassword = { viewModel.changePasswordVisible2(passwordVisible2) },
            onTextFieldChanged = {
                viewModel.onTextFieldChange(nameUser, lastnameUser, email, phoneNumberUser, password, it)
            }
        )
        Spacer(modifier = Modifier.padding(25.dp)) //Spacio


        BotonRegistrarse(registerEnable) {
            viewModel.createAuth(nameUser,lastnameUser,phoneNumberUser,email,password){
                navController.navigate(ProjectScreens.LocationScreen.name)
            }
        }
    }
}

@Composable
fun TextTitleRegisterScreen() {
    Text(text = "Registrate",
        fontFamily = lusitanaBoldFont,
        color = WhiteBone,
        fontSize = 48.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCommonRegister(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                            onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldNumberRegister(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                            onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = GrayDark.copy(alpha = 0.9f),
            textColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            placeholderColor = WhiteBone
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPasswordCommonField(atributeRequired: String,
                  passwordVisible: Boolean,
                  labelText:String,
                  placeholderText: String,
                  funShowPassword: () -> Unit,
                  onTextFieldChanged: (String) -> Unit){
    //Variables

    //Para Cambio Visual
    val visualTransformation = if (passwordVisible)
        VisualTransformation.None
    else PasswordVisualTransformation()

    TextField(
        value = atributeRequired,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
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
            if(atributeRequired.isNotEmpty()){
                PasswordVisibleIcon(passwordVisible, funShowPassword)
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: Boolean, funShowPassword: () -> Unit) {
    val image =
        if (passwordVisible)
            Icons.Default.VisibilityOff
        else Icons.Default.Visibility
    IconButton(onClick = { funShowPassword() }) {
        Icon(
            imageVector = image,
            contentDescription = "",
            tint = WhiteBone
        )
    }
}

@Composable
fun BotonRegistrarse(registerEnable: Boolean, function: () -> Unit){
    Button(
        onClick = {
            function()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        enabled = registerEnable,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "Registrarse",
            fontFamily = alegreyaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}
