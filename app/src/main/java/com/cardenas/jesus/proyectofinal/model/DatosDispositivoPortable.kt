package com.cardenas.jesus.proyectofinal.model

class DatosDispositivoPortable(
    val id: String,
    val fecha: String,
    val contaminantes: HashMap<String, Double>,
    val latitud: Double,
    val longitud: Double
) {
}