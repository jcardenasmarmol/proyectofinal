package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.model.DatosCiudadesWAQI
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    companion object {
        private fun generateOkHttpBuilder() : OkHttpClient {
            return OkHttpClient().newBuilder().build()
        }
        fun generateRetrofitWAQIInstance() : Retrofit {

            val builder = GsonBuilder()
            builder.registerTypeAdapter(DatosAirQualityModel::class.java, Deserializer())

            return Retrofit.Builder()
                .baseUrl(AppConstants.ENDPOINTWAQI)
                .client(generateOkHttpBuilder())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build()
        }
        fun generateRetrofitWAQICiudadInstance() : Retrofit {

            val builder = GsonBuilder()
            builder.registerTypeAdapter(DatosCiudadesWAQI::class.java, DeserializerCiudad())

            return Retrofit.Builder()
                .baseUrl(AppConstants.ENDPOINTWAQI)
                .client(generateOkHttpBuilder())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build()
        }

        fun generateRetrofitAPIInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(AppConstants.ENDPOINT)
                .client(generateOkHttpBuilder())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

    }
}