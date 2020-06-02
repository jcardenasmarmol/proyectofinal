package com.cardenas.jesus.proyectofinal.modelo.mapper

import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.historicos.DatosHistoricosDTO

class DatosMapper {

    fun transformarListaDatosHistoricos(items: List<DatosHistoricosDTO>?) : List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformarUnDatoHistorico(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformarUnDatoHistorico(items: List<DatosHistoricosDTO>) : DatosCalidadAire {
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

    fun transformarUltimosDatosArduino(items: DatosArduinoUltimosDTO?) : MutableList<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()


        if (items?.spainData?.isNotEmpty()!!)
            lista.add(transformarDatoArduino(items?.spainData))
        if (items?.greeceData?.isNotEmpty()!!)
            lista.add(transformarDatoArduino(items?.greeceData))
        if (items?.bulgariaData?.isNotEmpty()!!)
            lista.add(transformarDatoArduino(items?.bulgariaData))

        return lista
    }

    fun transformarListaDatosArduino(items: List<DatosArduinoDTO>?) : List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        items?.groupBy {
            it.date
        }?.map {
            lista.add(transformarDatoArduino(it.value))
        }

        return lista.sortedBy { it.fecha }
    }

    private fun transformarDatoArduino(items: List<DatosArduinoDTO>?) : DatosCalidadAire{
        var hashMap = HashMap<String, Double>()
        items?.map {
            it?.valor?.let {valor ->
                hashMap[it.contaminante.toLowerCase()] = valor
            }

        }
        val city = when (items?.elementAtOrNull(0)?.estacion){
            "Spain" -> "Sevilla, España"
            "Bulgaria" -> "Sofía, Bulgaria"
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