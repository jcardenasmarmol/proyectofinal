package com.cardenas.jesus.proyectofinal.model

class DatosAirQualityModel(
    val id: String,
    val city: String,
    val fecha: String,
    val contaminantes: HashMap<String, Double>
){
    override fun equals(other: Any?): Boolean {
        var iguales = false
        if (other is DatosAirQualityModel){
            if (other.id == this.id)
                iguales = true
        }
        return iguales
    }

}