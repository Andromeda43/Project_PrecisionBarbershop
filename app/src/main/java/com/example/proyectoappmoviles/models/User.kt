package com.example.proyectoappmoviles.models

data class User(
    val userId: String?,
    val name: String,
    val lastname: String,
    val email: String,
    val phonenNumber: String
    ){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user_id" to this.userId!!,
            "name" to this.name,
            "lastname" to this.lastname,
            "email" to this.email,
            "phonenumber" to this.phonenNumber
        )
    }
}
