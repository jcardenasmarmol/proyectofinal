package com.cardenas.jesus.proyectofinal.ui.ultimosNuestros

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class UltimosNuestrosViewModel : ViewModel() {

    fun getUltimos() : MutableLiveData<List<DatosAirQualityModel>> {
        return Repositorio.getUltimosNuestros()
    }

    class UltimosNuestrosViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UltimosNuestrosViewModel() as T
        }

    }
}