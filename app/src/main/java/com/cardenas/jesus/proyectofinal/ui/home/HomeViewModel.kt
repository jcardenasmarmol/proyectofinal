package com.cardenas.jesus.proyectofinal.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.utilidades.AppConstants
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class HomeViewModel : ViewModel() {

    private var estaciones = listOf(AppConstants.SEVILLACODE, AppConstants.GREECECODE, AppConstants.SOFIACODE)

    fun GetDatos() : List<MutableLiveData<DatosCalidadAire>> {
        var list = mutableListOf<MutableLiveData<DatosCalidadAire>>()
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