package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WAQIServices {
    @GET("feed/{estacion}/?token="+AppConstants.TOKENWAQI)
    fun requestLastDataFromStation(@Path("estacion") estacion : String) : Call<DatosAirQualityModel>

    @GET("search/?token="+AppConstants.TOKENWAQI)
    fun requestDataFromCitySearched(@Query("keyword") ciudad: String) : Call<List<DatosAirQualityModel>>
}