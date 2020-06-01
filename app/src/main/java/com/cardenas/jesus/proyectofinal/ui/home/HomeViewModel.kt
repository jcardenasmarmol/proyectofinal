package com.cardenas.jesus.proyectofinal.ui.home

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.utilidades.AppConstants
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class HomeViewModel(var fragmentManager: FragmentManager) : ViewModel() {

    private var estaciones =
        listOf(AppConstants.SEVILLACODE, AppConstants.GREECECODE, AppConstants.SOFIACODE)

    fun GetDatos(): List<MutableLiveData<DatosCalidadAire>> {
        var list = mutableListOf<MutableLiveData<DatosCalidadAire>>()
        estaciones.map {
            list.add(Repositorio.getData(fragmentManager, it))
        }
        return list
    }

    class HomeViewModelFactory(var fragmentManager: FragmentManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(fragmentManager) as T
        }
    }
}