package com.cardenas.jesus.proyectofinal.webservices.waqi

import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.utilidades.AppConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WAQIServices {
    @GET("feed/{estacion}/?token="+ AppConstants.TOKENWAQI)
    fun requestLastDataFromStation(@Path("estacion") estacion : String) : Call<DatosCalidadAire>
}