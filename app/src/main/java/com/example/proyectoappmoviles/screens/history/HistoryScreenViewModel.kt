package com.example.proyectoappmoviles.screens.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Schedules
import com.example.proyectoappmoviles.models.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HistoryScreenViewModel {
    //Variable para la conexion de Base de Datos
    val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    //Variables para leer las reservas
    private val _schedulesRegistered = MutableLiveData<List<Schedules>>()
    val schedulesRegistered: LiveData<List<Schedules>> = _schedulesRegistered

    fun getSchedules(){
        val refsDatesTemp: MutableList<Schedules> = mutableListOf()
        val gson = Gson()

        val doc = db.collection("date").get().addOnCompleteListener(){ result ->
            for(dateInstance in result.result){
                if (dateInstance.exists()){
                    //Seleccionamos solamente las reservas hechas por nosotros
                    currentUser?.let { Log.d("user_uid Current: ", it) }

                    if (dateInstance.data["user_uid"] == currentUser){
                        //referencia para obtener el barbero
                        val refBarberMap = dateInstance.data["barberobj"] as Map<*, *>
                        val refBarberJson = gson.toJson(refBarberMap)
                        //Parseamos el barbero desde el Json
                        val barberAttend = gson.fromJson(refBarberJson, Barber::class.java)

                        //Obtenemos el Json de los Servicios
                        val refListServicesMap = dateInstance.data["services_include"]

                        val refListServicesJson = gson.toJson(refListServicesMap)
                        Log.d("listJsonServices: ", refListServicesJson)

                        val servicesInclude = gson.fromJson(refListServicesJson, Array<Service>::class.java).toList()
                        Log.d("servicesInclude: ", servicesInclude.toString())


                        val dateTemp = Schedules.toObjectDate(dateInstance)
                        dateTemp.barberAttend = barberAttend
                        dateTemp.servicesInclude = servicesInclude.toMutableList()

                        refsDatesTemp.add(dateTemp)
                    }
                }
            }
            _schedulesRegistered.value = refsDatesTemp
        }
    }

}