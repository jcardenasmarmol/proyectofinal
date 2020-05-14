package com.cardenas.jesus.proyectofinal.ui.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.model.DatosDispositivoPortable
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class DispMovilMapsViewModel : ViewModel() {

    fun getDatos() : MutableLiveData<List<DatosDispositivoPortable>> {
        return Repositorio.getDatosDispPortables()
    }

    class DispMovilMapsViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DispMovilMapsViewModel() as T
        }

    }
}