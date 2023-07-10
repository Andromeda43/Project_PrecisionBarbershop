package com.example.proyectoappmoviles.screens.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Schedules
import com.example.proyectoappmoviles.models.Service
import com.example.proyectoappmoviles.screens.barbers.BarberScreenViewModel
import com.example.proyectoappmoviles.screens.services.ServicesScreenViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.util.Date

@Suppress("DEPRECATION")
class ScheduleScreenViewModel {
    //Variable para la conexion de Base de Datos
    val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    //Lista de los servicios Añadidos
    val servicesAdded:List<Service> = ServicesScreenViewModel.servicesAdded
    //Objeto del barbero Seleccionado
    val barberSelect: Barber? = BarberScreenViewModel.barberSelect
    //Datos para los horarios.
    private val _mDate = MutableLiveData<Timestamp>()
    val mDate: LiveData<Timestamp> = _mDate
    //Citas ya agendadas
    private val _datesRegistered = MutableLiveData<List<Schedules>>()
    val datesRegistered: LiveData<List<Schedules>> = _datesRegistered
    //Horarios enlazados por horas
    val scheduleTurns = mutableListOf("9", "10", "11", "12", "14", "15", "16", "17", "18", "19")


    fun modifyDate(mYear: Int, mMonth: Int, mDayOfMonth: Int){
        _mDate.value = Timestamp(Date(mYear, mMonth, mDayOfMonth))
    }

    fun getDates(){
        val refsDatesTemp: MutableList<Schedules> = mutableListOf()
        val gson = Gson()

        val doc = db.collection("date").get().addOnCompleteListener(){ result ->
            for(dateInstance in result.result){
                if (dateInstance.exists()){
                    //referencia para obtener el barbero
                    val refBarberMap = dateInstance.data["barberobj"] as Map<*, *>
                    val refBarberJson = gson.toJson(refBarberMap)
                    //Parseamos el barbero desde el Json
                    val barberAttend = gson.fromJson(refBarberJson, Barber::class.java)

                    //Obtenemos el Json de los Servicios
                    val refListServicesMap = dateInstance.data["services_include"]
                    val refListServicesJson = gson.toJson(refListServicesMap)

                    val servicesInclude = gson.fromJson(refListServicesJson, Array<Service>::class.java).toList()

                    val dateTemp = Schedules.toObjectDate(dateInstance)
                    dateTemp.barberAttend = barberAttend
                    dateTemp.servicesInclude = servicesInclude.toMutableList()

                    refsDatesTemp.add(dateTemp)
                }
            }
            _datesRegistered.value = refsDatesTemp
        }
    }

    fun filterByDate(dateSelect: Timestamp): MutableList<Schedules> {
        val refsDatesTemp: MutableList<Schedules> = mutableListOf()
        if (_datesRegistered.value?.isNotEmpty() == true){
            for (dateInstance in _datesRegistered.value!!){
                if (dateInstance.datetime.toDate().month == dateSelect.toDate().month){
                    if (dateInstance.datetime.toDate().date == dateSelect.toDate().date){
                        refsDatesTemp.add(dateInstance)
                    }
                }
            }
        }
        return refsDatesTemp
    }

    fun turnAvabilable(datesInDay: MutableList<Schedules>, scheduleHour: Int, barberSelected: Barber): Boolean {
        if (datesInDay.isNotEmpty()){
            for (dateInDay in datesInDay){
                if (barberSelected.id_barber == dateInDay.barberAttend?.id_barber){
                    if ((dateInDay.datetime.toDate().hours).toString() == scheduleTurns[scheduleHour]){
                        return false
                    }
                }
            }
        }
        return true
    }

    fun createSchedule(dateSelected: Timestamp, hourSelected: String): Schedules {

        val mYear = dateSelected.toDate().year
        val mMonth = dateSelected.toDate().month
        val mDay = dateSelected.toDate().date
        val dateTimeStampComplete = Timestamp(Date(mYear, mMonth, mDay, hourSelected.toInt(),0,0))

        val tempSchedule = Schedules("",dateTimeStampComplete, currentUser)

        tempSchedule.barberAttend = barberSelect
        if (servicesAdded.isEmpty()){
            tempSchedule.servicesInclude = mutableListOf(null)
        }else tempSchedule.servicesInclude = (servicesAdded as MutableList<Service>).toMutableList()



        return tempSchedule
    }

    fun postSchedule(schCreate: Schedules){
        val scheduleToPost = schCreate.toMap()

        FirebaseFirestore.getInstance().collection("date")
            .add(scheduleToPost)
            .addOnSuccessListener {
                Log.d("respuestadb", "Creado Correctamente")
            }
            .addOnFailureListener {
                Log.d("respuestadb", "Fallo Con Ganas")
            }
    }

    fun setScheduleCreate(schedules: Schedules){
        ScheduleScreenViewModel.dateCreated = schedules
    }

    companion object{
        var dateCreated: Schedules? = null

    }

}