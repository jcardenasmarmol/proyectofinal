package com.cardenas.jesus.proyectofinal.model.dto.arduino

import com.google.gson.annotations.SerializedName

class DatosArduinoDTO(
    @SerializedName("estacion") val estacion: String,
    @SerializedName("fecha") val date: String,
    @SerializedName("contaminante") val contaminante: String,
    @SerializedName("valor") val valor: Double?
)