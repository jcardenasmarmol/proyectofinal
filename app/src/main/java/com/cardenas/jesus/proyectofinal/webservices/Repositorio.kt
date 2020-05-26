package com.cardenas.jesus.proyectofinal.webservices

import androidx.lifecycle.MutableLiveData
import com.cardenas.jesus.proyectofinal.BodyClass
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosDTO
import com.cardenas.jesus.proyectofinal.model.mapper.DatosMapper
import com.cardenas.jesus.proyectofinal.webservices.waqi.WAQIServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Repositorio {
    companion object {
        private val apiWAQI = ApiClient.generateRetrofitWAQIInstance().create<WAQIServices>()
        private val nuestraAPI = ApiClient.generateRetrofitAPIInstance().create<ResourceServices>()
        fun getData(estacion: String): MutableLiveData<DatosAirQualityModel> {
            var datos = MutableLiveData<DatosAirQualityModel>()

            apiWAQI.requestLastDataFromStation(estacion)
                .enqueue(object : Callback<DatosAirQualityModel> {
                    override fun onFailure(call: Call<DatosAirQualityModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<DatosAirQualityModel>,
                        response: Response<DatosAirQualityModel>
                    ) {
                        datos.value = response.body()
                    }

                })

            return datos
        }

        fun getData(estacion : Int, fechaI : String, fechaF : String): MutableLiveData<List<DatosAirQualityModel>>{
            var datos = MutableLiveData<List<DatosAirQualityModel>>()
            nuestraAPI.requestHistories(BodyClass(estacion, fechaI, fechaF))
                .enqueue(object : Callback<List<DatosHistoricosDTO>>{
                    override fun onFailure(call: Call<List<DatosHistoricosDTO>>, t: Throwable) {

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

        fun getData(estacion : String, fechaI : String, fechaF : String): MutableLiveData<List<DatosAirQualityModel>>{
            var datos = MutableLiveData<List<DatosAirQualityModel>>()
            nuestraAPI.requesArduinoFecha(estacion, fechaI, fechaF)
                .enqueue(object : Callback<List<DatosArduinoDTO>>{
                    override fun onFailure(call: Call<List<DatosArduinoDTO>>, t: Throwable) {
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

        fun getUltimosNuestros() : MutableLiveData<List<DatosAirQualityModel>> {
            var datos = MutableLiveData<List<DatosAirQualityModel>>()
            nuestraAPI.requestLastArduino()
                .enqueue(object : Callback<DatosArduinoUltimosDTO>{
                    override fun onFailure(call: Call<DatosArduinoUltimosDTO>, t: Throwable) {
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