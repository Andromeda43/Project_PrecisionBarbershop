package com.example.proyectoappmoviles.models

import com.google.firebase.firestore.DocumentSnapshot

data class Barber(
    val id_barber: String,
    val firstname: String,
    val lastname: String,
    val experience: Long,
    val rating: Int,
    val imgref: String
){
    companion object {
        fun toObjectBarber(obj: DocumentSnapshot?): Barber {
                return Barber(
                    obj?.data!!["id_barber"].toString(),
                    obj.data!!["firstname"].toString(),
                    obj.data!!["lastname"].toString(),
                    obj.data!!["experience"].toString().toLong(),
                    obj.data!!["rating"].toString().toInt(),
                    obj.data!!["imgref"].toString()
                )
        }
    }
}

