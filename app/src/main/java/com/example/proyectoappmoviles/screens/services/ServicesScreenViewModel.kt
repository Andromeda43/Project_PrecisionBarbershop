package com.example.proyectoappmoviles.screens.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.models.Service
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ServicesScreenViewModel {
    //Conexion a la base de datos
    val db = Firebase.firestore
    //Variables para los servicios incluidos
    private val _servicesRegistered = MutableLiveData<List<Service>>()
    val servicesRegistered : LiveData<List<Service>> = _servicesRegistered
    //Variables para los servicios añadidos por el usuario

    companion object{
        fun clearServicesAdded() {
            _servicesAdded.clear()
        }

        private val _servicesAdded: MutableList<Service> = mutableListOf()
        val servicesAdded: List<Service> = _servicesAdded
    }


    fun getServicesFromDB(){
        val servicesTemp: MutableList<Service> = mutableListOf()
        val doc = db.collection("service").get().addOnCompleteListener(){ result ->
            for(serviceInstance in result.result){
                if (serviceInstance.exists()){

                    val serviceTemp = Service.toObjectSede(serviceInstance)
                    servicesTemp.add(serviceTemp)
                }
            }
            _servicesRegistered.value = servicesTemp
        }
    }

    fun updateServicesAddes(serviceItem: Service) {
        if (_servicesAdded.isNotEmpty()){
            for (index in 0 until _servicesAdded.size){
                if (_servicesAdded[index].name_service == serviceItem.name_service){
                    _servicesAdded.removeAt(index)
                    break
                }
                if (_servicesAdded.last().name_service != serviceItem.name_service){
                    _servicesAdded.add(serviceItem)
                }
            }
        }else{
            _servicesAdded.add(serviceItem)
        }
    }

    fun isAdded(serviceItem: Service): Boolean{
        for (serv in _servicesAdded) {
            if (serv.name_service == serviceItem.name_service) {
                return true
            }
        }
        return false
    }
}