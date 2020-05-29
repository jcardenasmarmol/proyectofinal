package com.cardenas.jesus.proyectofinal.modelo.mapper

import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.historicos.DatosHistoricosDTO

class DatosMapper {

    fun transformListaDatosHistoricos(items: List<DatosHistoricosDTO>?) : List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformDatoHistorico(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformDatoHistorico(items: List<DatosHistoricosDTO>) : DatosCalidadAire {
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

        return DatosCalidadAire(
            items?.elementAtOrNull(0)?.estacion.toString(),
            city,
            items?.get(0)?.date ?: "",
            hashMap)
    }

    fun transformUltimosDatosArduino(items: DatosArduinoUltimosDTO?) : MutableList<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()


        if (items?.spainData?.isNotEmpty()!!)
            lista.add(transformDatoArduino(items?.spainData))
        if (items?.bulgariaData?.isNotEmpty()!!)
            lista.add(transformDatoArduino(items?.bulgariaData))
        if (items?.greeceData?.isNotEmpty()!!)
            lista.add(transformDatoArduino(items?.greeceData))

        return lista
    }

    fun transformListaDatosArduino(items: List<DatosArduinoDTO>?) : List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformDatoArduino(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformDatoArduino(items: List<DatosArduinoDTO>?) : DatosCalidadAire{
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

        return DatosCalidadAire(
            items?.elementAtOrNull(0)?.estacion ?: "",
            city,
            items?.elementAtOrNull(0)?.date ?: "",
            hashMap)
    }

}