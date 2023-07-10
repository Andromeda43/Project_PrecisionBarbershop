package com.example.proyectoappmoviles.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Schedules(
    val idDocument: String?,
    val datetime: Timestamp,
    val id_user: String?,
    ){
    var barberAttend: Barber? = null
    var servicesInclude: MutableList<Service?> = mutableListOf()

    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "datetime" to this.datetime,
            "barberobj" to this.barberAttend!! ,
            "user_uid" to this.id_user!!,
            "services_include" to this.servicesInclude
        )
    }

    companion object{
        fun toObjectDate(obj: QueryDocumentSnapshot): Schedules {
            return Schedules(obj.id, obj.data["datetime"] as Timestamp, obj.data["id_user"].toString())
        }
    }
}
