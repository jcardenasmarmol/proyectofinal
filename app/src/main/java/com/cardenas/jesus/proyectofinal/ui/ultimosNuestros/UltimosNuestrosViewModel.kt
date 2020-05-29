package com.cardenas.jesus.proyectofinal.ui.ultimosNuestros

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.webservices.Repositorio

class UltimosNuestrosViewModel : ViewModel() {

    fun getUltimos() : MutableLiveData<MutableList<DatosCalidadAire>> {
        return Repositorio.getUltimosNuestros()
    }

    class UltimosNuestrosViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UltimosNuestrosViewModel() as T
        }

    }
}