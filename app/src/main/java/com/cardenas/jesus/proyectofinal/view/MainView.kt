package com.cardenas.jesus.proyectofinal.view

import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel

interface MainView {
    fun setDataSet(result: List<DatosAirQualityModel?>)
}