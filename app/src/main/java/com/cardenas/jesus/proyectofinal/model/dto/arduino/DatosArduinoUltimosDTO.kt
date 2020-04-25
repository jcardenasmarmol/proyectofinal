package com.cardenas.jesus.proyectofinal.model.dto.arduino

import com.google.gson.annotations.SerializedName

class DatosArduinoUltimosDTO(
    @SerializedName("spain") val spainData: List<DatosArduinoDTO>,
    @SerializedName("greece") val greeceData: List<DatosArduinoDTO>,
    @SerializedName("bulgarian") val bulgariaData: List<DatosArduinoDTO>
)

