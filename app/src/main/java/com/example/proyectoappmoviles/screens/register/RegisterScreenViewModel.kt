package com.example.proyectoappmoviles.screens.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterScreenViewModel {
    //Para crear el usuario en FireBase
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    //Atributos cambiantes que seran usados en el RegisterScreen
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    private val _nameUser = MutableLiveData<String>()
    val nameUser: LiveData<String> = _nameUser

    private val _lastnameUser = MutableLiveData<String>()
    val lastnameUser: LiveData<String> = _lastnameUser

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _passwordVisible1 = MutableLiveData<Boolean>()
    val passwordVisible1: LiveData<Boolean> = _passwordVisible1

    private val _passwordVisible2 = MutableLiveData<Boolean>()
    val passwordVisible2: LiveData<Boolean> = _passwordVisible2

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable


    //Funciones que comprueban el email y el password
    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6
    private fun isEqualPassword(p1: String, p2: String): Boolean = p1 == p2

    //Funcion para asumir el cambio cuando se escriba en un TextField
    fun onTextFieldChange(nameUser:String, lastnameUser: String,
                          email: String, phoneNumber: String ,password: String,
                          confirmPassword: String){
        _nameUser.value = nameUser
        _lastnameUser.value = lastnameUser
        _email.value = email
        _phoneNumber.value = phoneNumber
        _password.value = password
        _confirmPassword.value = confirmPassword
        _registerEnable.value = !_nameUser.value.isNullOrBlank() && !_lastnameUser.value.isNullOrBlank() &&
                isValidEmail(email) && isValidPassword(password) &&
                isValidPassword(confirmPassword) && isEqualPassword(password, confirmPassword)
    }
    //Funcion para activar o desactivar ver la contraseña
    fun changePasswordVisible1(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible1.value = newPasswordVisible
    }
    fun changePasswordVisible2(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible2.value = newPasswordVisible
    }

    fun createAuth(nameUser: String, lastnameUser: String,
                   phoneNumber:String, email: String,
                   password: String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    createUser(nameUser, lastnameUser, email, phoneNumber)
                    home()
                }
                else{
                    Log.d("", "La tarea fallo")
                }
                _loading.value = false
            }
        }
    }

    private fun createUser(nameUser: String,
                           lastnameUser: String,
                            email: String,
                            phoneNumber: String) {
        val userId = auth.currentUser?.uid
        val user = User(userId, nameUser, lastnameUser, email, phoneNumber).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("respuestadb", "Creado Correctamente")
            }
            .addOnFailureListener {
                Log.d("respuestadb:", "Fallo Con Ganas")
            }
    }
}