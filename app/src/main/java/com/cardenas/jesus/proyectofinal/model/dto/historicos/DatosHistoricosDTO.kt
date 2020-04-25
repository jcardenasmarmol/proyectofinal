package com.cardenas.jesus.proyectofinal.model.dto.historicos

import com.google.gson.annotations.SerializedName

class DatosHistoricosDTO(
    @SerializedName("estacion") val estacion: Int,
    @SerializedName("fecha") val date: String,
    @SerializedName("contaminante") val contaminante: String,
    @SerializedName("valor") val valor: Double
)