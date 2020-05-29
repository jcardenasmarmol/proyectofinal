package com.cardenas.jesus.proyectofinal.modelo

class DatosCalidadAire(
    val id: String,
    val city: String,
    val fecha: String,
    val contaminantes: HashMap<String, Double>
)