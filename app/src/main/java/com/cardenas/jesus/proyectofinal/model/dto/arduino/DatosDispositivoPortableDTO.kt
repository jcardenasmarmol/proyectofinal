package com.cardenas.jesus.proyectofinal.model.dto.arduino

import com.google.gson.annotations.SerializedName

class DatosDispositivoPortableDTO(
    @SerializedName("estacion") val estacion: String,
    @SerializedName("fecha") val date: String,
    @SerializedName("contaminante") val contaminante: String,
    @SerializedName("valor") val valor: Double,
    @SerializedName("lat") val latitud: Double,
    @SerializedName("long") val longitud: Double
) {
}