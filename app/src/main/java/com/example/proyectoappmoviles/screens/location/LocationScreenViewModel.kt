package com.example.proyectoappmoviles.screens.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Barber.Companion.toObjectBarber
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.models.Sede.Companion.toObjectSede
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//Constante para permisos
const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234 //Constante de permisos


class LocationScreenViewModel {
    val db = Firebase.firestore
    //lateinit var sedes: ArrayList<Sede>
    private val _sedesRegistered = MutableLiveData<List<Sede>>()
    val sedesRegistered : LiveData<List<Sede>> = _sedesRegistered
    //Permisos para acceder a la ubicacion
    private val _locationPermissionGranted = MutableLiveData<Boolean>()
    val locationPermissionGranted: LiveData<Boolean> = _locationPermissionGranted

    //Para saber que sede se selecciona al final
    fun setSedeSelect(sede: Sede){
        sedeSelect = sede
    }

    private fun parserReferences(DcRef: List<DocumentReference>, funct: (Barber) -> Boolean): MutableList<Barber> {
        val refsb: MutableList<Barber> = mutableListOf()

        for (i in DcRef){
            i.get().addOnCompleteListener(){result ->
                val barberTemp = toObjectBarber(result.result)
                funct(barberTemp)
            }
        }
        Log.d("BarberList:", refsb.toString())

        return refsb
    }

    fun getSedes(){
        val refsSedesTemp: MutableList<Sede> = mutableListOf()
        val doc = db.collection("sedes").get().addOnCompleteListener(){ result ->
            for(sedeInstance in result.result){
                if (sedeInstance.exists()){

                    val refsBarber = sedeInstance.data["barbers"] as List<DocumentReference>
                    val sedeTemp = toObjectSede(sedeInstance)
                    parserReferences(refsBarber) { b: Barber -> sedeTemp.barbersList.add(b) }
                    refsSedesTemp.add(sedeTemp)
                }
            }
            _sedesRegistered.value = refsSedesTemp
        }
    }

    fun changePermissionValue() {
        _locationPermissionGranted.value = true
    }

    //Cosas que pueden ser llamadas desde otra clase sin necesidad de instanciar la clase
    companion object {
        var sedeSelect: Sede? = null
    }
}
