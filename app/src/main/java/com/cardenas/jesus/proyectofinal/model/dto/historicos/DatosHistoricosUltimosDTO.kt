package com.cardenas.jesus.proyectofinal.model.dto.historicos

import com.google.gson.annotations.SerializedName

class DatosHistoricosUltimosDTO(
    @SerializedName("spain") val spainData: List<DatosHistoricosDTO>,
    @SerializedName("greece") val greeceData: List<DatosHistoricosDTO>,
    @SerializedName("bulgarian") val bulgariaData: List<DatosHistoricosDTO>
) {

}