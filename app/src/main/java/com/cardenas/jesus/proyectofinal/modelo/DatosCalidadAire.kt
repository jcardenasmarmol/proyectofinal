package com.cardenas.jesus.proyectofinal.modelo

class DatosCalidadAire(
    val id: String,
    val ciudad: String,
    val fecha: String,
    val contaminantes: HashMap<String, Double>
){

    override fun equals(other: Any?): Boolean {
        var sonIguales = false
        if (other is DatosCalidadAire){
            if (other.id == this.id && other.ciudad == this.ciudad && other.fecha == this.fecha && other.contaminantes == this.contaminantes)
                sonIguales = true
        }
        return sonIguales
    }

}