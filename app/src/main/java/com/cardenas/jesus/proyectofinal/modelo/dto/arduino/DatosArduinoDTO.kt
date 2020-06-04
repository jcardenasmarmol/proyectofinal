package com.cardenas.jesus.proyectofinal.modelo.dto.arduino

import com.google.gson.annotations.SerializedName

class DatosArduinoDTO(
    @SerializedName("estacion") val estacion: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("contaminante") val contaminante: String,
    @SerializedName("valor") val valor: Double?
)