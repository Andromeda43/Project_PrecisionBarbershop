package com.example.proyectoappmoviles.models

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Service(
    val name_service: String,
    val price: Long,
    val imgref: String
){

    companion object{
        fun toObjectSede(obj: QueryDocumentSnapshot): Service {
            return Service(obj.data["name_service"].toString(),
                obj.data["price"].toString().toLong(),
                obj.data["imgref"].toString()
                )
        }
    }
}
