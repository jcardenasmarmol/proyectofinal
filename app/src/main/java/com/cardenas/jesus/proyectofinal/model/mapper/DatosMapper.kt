package com.cardenas.jesus.proyectofinal.model.mapper

import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.model.DatosDispositivoPortable
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.model.dto.arduino.DatosDispositivoPortableDTO
import com.cardenas.jesus.proyectofinal.model.dto.historicos.DatosHistoricosDTO

class DatosMapper {

    fun transformDatosArduinoUltimos(items: DatosArduinoUltimosDTO?) : List<DatosAirQualityModel> {
        var lista = mutableListOf<DatosAirQualityModel>()


        if (items?.spainData?.isNotEmpty()!!)
            lista.add(transform(items?.spainData))
        if (items?.bulgariaData?.isNotEmpty()!!)
            lista.add(transform(items?.bulgariaData))
        if (items?.greeceData?.isNotEmpty()!!)
            lista.add(transform(items?.greeceData))

        return lista
    }

    fun transformDatosArduino(items: List<DatosArduinoDTO>?) : List<DatosAirQualityModel> {
        var lista = mutableListOf<DatosAirQualityModel>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transform(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    fun transformDatosHistoricos(items: List<DatosHistoricosDTO>?) : List<DatosAirQualityModel> {
        var lista = mutableListOf<DatosAirQualityModel>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformUno(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformUno(items: List<DatosHistoricosDTO>) : DatosAirQualityModel {
        var hashMap = HashMap<String, Double>()
        items?.map {
            hashMap[it.contaminante.toLowerCase()] = it.valor
        }

        val city = when (items?.get(0)?.estacion){
            8495 -> "Sevilla, España"
            8084 -> "Sofía, Bulgaria"
            12410 -> "Chania, Greece"
            else -> ""
        }

        return DatosAirQualityModel(
            items?.elementAtOrNull(0)?.estacion.toString(),
            city,
            items?.get(0)?.date ?: "",
            hashMap)
    }

    private fun transform(items: List<DatosArduinoDTO>?) : DatosAirQualityModel{
        var hashMap = HashMap<String, Double>()
        items?.map {
            it?.valor?.let {valor ->
                hashMap[it.contaminante.toLowerCase()] = valor
            }

        }
        val city = when (items?.elementAtOrNull(0)?.estacion){
            "Spain" -> "Sevilla, España"
            "Bulgarian" -> "Sofía, Bulgaria"
            "Greece" -> "Chania, Greece"
            else -> ""
        }

        return DatosAirQualityModel(
            items?.elementAtOrNull(0)?.estacion ?: "",
            city,
            items?.elementAtOrNull(0)?.date ?: "",
            hashMap)
    }

    fun transformDatosDispositivoPortable(items: List<DatosDispositivoPortableDTO>?) : List<DatosDispositivoPortable> {
        var lista = mutableListOf<DatosDispositivoPortable>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformDatoDispPortable(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformDatoDispPortable(items : List<DatosDispositivoPortableDTO>) : DatosDispositivoPortable{
        var hashMap = HashMap<String, Double>()
        items?.map {
            it?.valor?.let {valor ->
                hashMap[it.contaminante.toLowerCase()] = valor
            }

        }

        return DatosDispositivoPortable(
            items?.elementAtOrNull(0)?.estacion ?: "",
            items?.elementAtOrNull(0)?.date ?: "",
            hashMap,
            items?.elementAtOrNull(0)?.latitud ?: 0.0,
            items?.elementAtOrNull(0)?.longitud ?: 0.0
        )
    }
}