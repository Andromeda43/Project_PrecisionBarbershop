package com.example.proyectoappmoviles.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Sede(
    val documentId: String?,
    val id_sede: String,
    val namesede: String,
    val telephone: String,
    val whatsnumber: String,
    val direction: String,
    val mapdir: GeoPoint,
){
    var barbersList: MutableList<Barber> = mutableListOf()

    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "id_sede" to this.id_sede,
            "namesede" to this.namesede,
            "telephone" to this.telephone,
            "whatsnumber" to this.whatsnumber,
            "direction" to this.direction,
            "mapdir" to this.mapdir,
            "babersList" to this.barbersList
        )
    }

    companion object {
        fun toObjectSede(obj: QueryDocumentSnapshot): Sede {
            return Sede(obj.id, obj.data["id_sede"].toString(),
                obj.data["namesede"].toString(), obj.data["telephone"].toString(),
                obj.data["whatsnumber"].toString(),obj.data["direction"].toString(),
                obj.data["mapdir"] as GeoPoint,
            )
        }
    }
}
