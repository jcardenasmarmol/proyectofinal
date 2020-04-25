package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WAQIServices {
    @GET("feed/{estacion}/?token="+AppConstants.TOKENWAQI)
    fun requestLastDataFromStation(@Path("estacion") estacion : String) : Call<DatosAirQualityModel>

    @GET("search/?token="+AppConstants.TOKENWAQI+"&keyword={ciudad}")
    fun requestDataFromCitySearched(@Path("ciudad") ciudad: String) : Call<List<DatosAirQualityModel>>
}