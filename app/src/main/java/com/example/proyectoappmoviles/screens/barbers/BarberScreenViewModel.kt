package com.example.proyectoappmoviles.screens.barbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoappmoviles.models.Barber
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.screens.location.LocationScreenViewModel

class BarberScreenViewModel {
    private val _sedeSelect: Sede? = LocationScreenViewModel.sedeSelect
    val sedeSelect: Sede? = _sedeSelect

    fun setBarberSelect(barber: Barber){
        barberSelect = barber
    }

    companion object {
        var barberSelect: Barber? = null
    }
}