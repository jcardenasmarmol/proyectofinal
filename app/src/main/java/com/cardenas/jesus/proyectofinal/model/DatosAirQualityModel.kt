package com.cardenas.jesus.proyectofinal.model

class DatosAirQualityModel(
    val id: String,
    val city: String,
    val fecha: String,
    val contaminantes: HashMap<String, Double>
)