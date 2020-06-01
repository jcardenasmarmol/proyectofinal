package com.cardenas.jesus.proyectofinal.ui.grafica

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class GraphViewModel(var fragmentManager: FragmentManager) : ViewModel() {

    fun getDatosOficiales(estacion : Int, fechaI : String, fechaF : String) : MutableLiveData<List<DatosCalidadAire>> {
        return Repositorio.getData(fragmentManager, estacion,fechaI,fechaF)
    }

    fun getDatosArduino(estacion : String, fechaI : String, fechaF : String) : MutableLiveData<List<DatosCalidadAire>> {
        return Repositorio.getData(fragmentManager, estacion,fechaI,fechaF)
    }

    class GraphViewModelFactory(var fragmentManager: FragmentManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GraphViewModel(fragmentManager) as T
        }
    }
}