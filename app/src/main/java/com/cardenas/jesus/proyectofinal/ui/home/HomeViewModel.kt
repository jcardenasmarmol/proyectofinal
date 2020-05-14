package com.cardenas.jesus.proyectofinal.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class HomeViewModel : ViewModel() {

    fun GetDatos(estaciones : List<String>) : List<MutableLiveData<DatosAirQualityModel>> {
        var list = mutableListOf<MutableLiveData<DatosAirQualityModel>>()
        estaciones.map {
            list.add(Repositorio.getData(it))
        }
        return list
    }

    class HomeViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel() as T
        }
    }
}