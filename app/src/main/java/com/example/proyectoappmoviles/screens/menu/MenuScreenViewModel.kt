package com.example.proyectoappmoviles.screens.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.screens.location.LocationScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MenuScreenViewModel {
    private val auth: FirebaseAuth = Firebase.auth
    private val _sedeSelect: Sede? = LocationScreenViewModel.sedeSelect
    val sedeSelect: Sede? = _sedeSelect

    //Funcion para Setear la sede escogica


    fun disconectUser() {
        auth.signOut()
    }

}