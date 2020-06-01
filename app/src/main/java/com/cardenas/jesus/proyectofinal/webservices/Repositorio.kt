package com.cardenas.jesus.proyectofinal.webservices

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.historicos.DatosHistoricosDTO
import com.cardenas.jesus.proyectofinal.modelo.mapper.DatosMapper
import com.cardenas.jesus.proyectofinal.utilidades.BodyClass
import com.cardenas.jesus.proyectofinal.utilidades.DialogoAlerta
import com.cardenas.jesus.proyectofinal.webservices.waqi.WAQIServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Repositorio {
    companion object {
        private val apiWAQI = ApiClient.generateRetrofitWAQIInstance().create<WAQIServices>()
        private val nuestraAPI = ApiClient.generateRetrofitAPIInstance().create<ResourceServices>()
        fun getData(fragmentManager: FragmentManager, estacion: String): MutableLiveData<DatosCalidadAire> {
            var datos = MutableLiveData<DatosCalidadAire>()

            apiWAQI.requestLastDataFromStation(estacion)
                .enqueue(object : Callback<DatosCalidadAire> {
                    override fun onFailure(call: Call<DatosCalidadAire>, t: Throwable) {
                        DialogoAlerta("Error: ${t.message}").show(fragmentManager, "Alerta")
                    }

                    override fun onResponse(
                        call: Call<DatosCalidadAire>,
                        response: Response<DatosCalidadAire>
                    ) {
                        datos.value = response.body()
                    }

                })

            return datos
        }

        fun getData(fragmentManager: FragmentManager, estacion : Int, fechaI : String, fechaF : String): MutableLiveData<List<DatosCalidadAire>>{
            var datos = MutableLiveData<List<DatosCalidadAire>>()
            nuestraAPI.requestHistories(
                BodyClass(
                    estacion,
                    fechaI,
                    fechaF
                )
            )
                .enqueue(object : Callback<List<DatosHistoricosDTO>>{
                    override fun onFailure(call: Call<List<DatosHistoricosDTO>>, t: Throwable) {
                        DialogoAlerta("Error: ${t.message}").show(fragmentManager, "Alerta")
                    }

                    override fun onResponse(
                        call: Call<List<DatosHistoricosDTO>>,
                        response: Response<List<DatosHistoricosDTO>>
                    ) {
                        if (response.isSuccessful){
                            datos.value = DatosMapper().transformListaDatosHistoricos(response.body())
                        }
                    }

                })
            return datos
        }

        fun getData(fragmentManager: FragmentManager, estacion : String, fechaI : String, fechaF : String): MutableLiveData<List<DatosCalidadAire>>{
            var datos = MutableLiveData<List<DatosCalidadAire>>()
            nuestraAPI.requesArduinoFecha(estacion, fechaI, fechaF)
                .enqueue(object : Callback<List<DatosArduinoDTO>>{
                    override fun onFailure(call: Call<List<DatosArduinoDTO>>, t: Throwable) {
                        DialogoAlerta("Error: ${t.message}").show(fragmentManager, "Alerta")
                    }

                    override fun onResponse(
                        call: Call<List<DatosArduinoDTO>>,
                        response: Response<List<DatosArduinoDTO>>
                    ) {
                        datos.value = DatosMapper().transformListaDatosArduino(response.body())
                    }


                })
            return datos
        }

        fun getUltimosNuestros(fragmentManager: FragmentManager) : MutableLiveData<MutableList<DatosCalidadAire>> {
            var datos = MutableLiveData<MutableList<DatosCalidadAire>>()
            nuestraAPI.requestLastArduino()
                .enqueue(object : Callback<DatosArduinoUltimosDTO>{
                    override fun onFailure(call: Call<DatosArduinoUltimosDTO>, t: Throwable) {
                        DialogoAlerta("Error: ${t.message}").show(fragmentManager, "Alerta")
                    }

                    override fun onResponse(
                        call: Call<DatosArduinoUltimosDTO>,
                        response: Response<DatosArduinoUltimosDTO>
                    ) {
                        datos.value = DatosMapper().transformUltimosDatosArduino(response.body())
                    }

                })
            return datos
        }

    }
}