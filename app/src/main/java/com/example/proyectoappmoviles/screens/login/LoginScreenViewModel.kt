package com.example.proyectoappmoviles.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel(){
    //Autenticaciones de FireBase
    private val auth: FirebaseAuth = Firebase.auth

    //Atributos usados en Compose
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _messageAuth = MutableLiveData<String>()
    val messageAuth: LiveData<String> = _messageAuth

    fun onLoginChange(email: String, password: String){
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    fun changePasswordVisible(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible.value = newPasswordVisible
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    //Funcion para iniciar sesion con FireBase autenticacion
    fun signInWithEmailAndPassword(email: String, password: String, function: ()->Unit)
    = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        _messageAuth.value = "Login Exitoso"
                        function()
                    }else{
                        _messageAuth.value = "Credenciales Incorrectas"
                    }
                }
        }catch (ex:Exception){
            Log.d("Excepcion","Except")
        }
    }
}