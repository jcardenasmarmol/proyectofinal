package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.BodyClass
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosDispositivoPortableDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosUltimosDTO
import retrofit2.Call
import retrofit2.http.*

interface ResourceServices {

    /**
     * No se usa
     */
    @GET("historicos/ultimos")
    fun requestLastHistories() : Call<DatosHistoricosUltimosDTO>

    @GET("arduino/ultimos")
    fun requestLastArduino() : Call<DatosArduinoUltimosDTO>

    @POST("historicos")
    fun requestHistories(@Body body: BodyClass) : Call<List<DatosHistoricosDTO>>

    @GET("arduino/portables")
    fun requestPortableDeviceData() : Call<List<DatosDispositivoPortableDTO>>

    @POST("arduino/{estacion}/{fechaInicial}/{fechaFinal}")
    fun requesArduinoFecha(@Path("estacion")estacion :String,
                           @Path("fechaInicial") fechaInicial: String,
                           @Path("fechaFinal") fechaFinal: String) : Call<List<DatosArduinoDTO>>

}