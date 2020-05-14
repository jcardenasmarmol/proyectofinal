package com.cardenas.jesus.proyectofinal.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.model.DatosCiudadesWAQI
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class SearchViewModel : ViewModel() {

    fun GetDatos(ciudad : String) : MutableLiveData<DatosCiudadesWAQI> {
        return Repositorio.getCiudades(ciudad)
    }

    class SearchViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel() as T
        }
    }
}