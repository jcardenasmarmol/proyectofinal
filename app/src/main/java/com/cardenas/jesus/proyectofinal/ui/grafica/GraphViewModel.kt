package com.cardenas.jesus.proyectofinal.ui.grafica

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class GraphViewModel : ViewModel() {

    fun getDatosOficiales(estacion : Int, fechaI : String, fechaF : String) : MutableLiveData<List<DatosAirQualityModel>> {
        return Repositorio.getData(estacion,fechaI,fechaF)
    }

    fun getDatosArduino(estacion : String, fechaI : String, fechaF : String) : MutableLiveData<List<DatosAirQualityModel>> {
        return Repositorio.getData(estacion,fechaI,fechaF)
    }

    class GraphViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GraphViewModel() as T
        }
    }
}