package com.cardenas.jesus.proyectofinal.webservices

import com.cardenas.jesus.proyectofinal.model.CiudadWAQI
import com.cardenas.jesus.proyectofinal.model.DatosCiudadesWAQI
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DeserializerCiudad : JsonDeserializer<DatosCiudadesWAQI>  {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DatosCiudadesWAQI {

        val ciudades = mutableListOf<CiudadWAQI>()

        json?.asJsonObject?.get("data")?.asJsonArray?.map {
            val ciudad = CiudadWAQI(
                it.asJsonObject.get("uid").asInt,
                it.asJsonObject.get("station").asJsonObject.get("name").asString)
            ciudades.add(ciudad)
        }


        return DatosCiudadesWAQI(
            ciudades
        )
    }
}