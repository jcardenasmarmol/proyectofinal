package com.cardenas.jesus.proyectofinal.ui.consultas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConsultaViewModel : ViewModel() {

    val fechaInicial = MutableLiveData<String>().apply {
        value = "2020-04-01"
    }

    val fechaFinal = MutableLiveData<String>().apply {
        value = "2020-04-07"
    }

    fun getEstaciones(consultaArduino : Boolean) : MutableLiveData<Array<String>>{
        return if (consultaArduino)
            MutableLiveData(arrayOf("Spain","Greece","Bulgaria"))
        else
            MutableLiveData(arrayOf("Bermejales, Sevilla, España @8495","Druzhba, Sofía, Bulgaria @8084", "Patra-2, Grecia @12410"))
    }

    class ConsultaViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ConsultaViewModel() as T
        }

    }
}