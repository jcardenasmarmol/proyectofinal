package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.historicos.DatosHistoricosDTO
import com.cardenas.jesus.proyectofinal.utilidades.BodyClass
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ResourceServices {

    @GET("arduino/ultimos")
    fun requestLastArduino() : Call<DatosArduinoUltimosDTO>

    @POST("historicos")
    fun requestHistories(@Body body: BodyClass) : Call<List<DatosHistoricosDTO>>

    @POST("arduino/{estacion}/{fechaInicial}/{fechaFinal}")
    fun requesArduinoFecha(@Path("estacion")estacion :String,
                           @Path("fechaInicial") fechaInicial: String,
                           @Path("fechaFinal") fechaFinal: String) : Call<List<DatosArduinoDTO>>

}