package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.BodyClass
import com.cardenas.jesus.proyectofinal.BodyClassArduino
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosDispositivoPortableDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosUltimosDTO
import retrofit2.Call
import retrofit2.http.*

interface ResourceServices {

    @GET("historicos/ultimos")
    fun requestLastHistories() : Call<DatosHistoricosUltimosDTO>

    @GET("arduino/ultimos")
    fun requestLastArduino() : Call<DatosArduinoUltimosDTO>

    @POST("historicos")
    fun requestHistories(@Body body: BodyClass) : Call<List<DatosHistoricosDTO>>

    @POST("arduino")
    fun requestArduino(@Body body: BodyClassArduino) : Call<List<DatosArduinoDTO>>

    @GET("arduino/portables")
    fun requestPortableDeviceData() : Call<List<DatosDispositivoPortableDTO>>
}