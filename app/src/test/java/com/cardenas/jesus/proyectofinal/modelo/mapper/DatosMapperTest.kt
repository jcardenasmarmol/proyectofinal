package com.cardenas.jesus.proyectofinal.modelo.mapper

import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.arduino.DatosArduinoUltimosDTO
import com.cardenas.jesus.proyectofinal.modelo.dto.historicos.DatosHistoricosDTO
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DatosMapperTest {

    private val mapper = DatosMapper()

    @Test
    fun testTransformarListaDatosHistoricos() {
        var obtenido = mapper.transformarListaDatosHistoricos(llenarDatosHistoricosDTO())
        var esperado = listaDatosCalidadAire()

        assertEquals(esperado, obtenido)
    }

    @Test
    fun testTransformarListaDatosArduino() {
        var obtenido = mapper.transformarListaDatosArduino(llenarListaDatosArduinoDTO())
        var esperado = listaDatosCalidadAireDesdeArduino()

        assertEquals(esperado, obtenido)
    }

    @Test
    fun testTransformarUltimosDatosArduino() {

        var obtenido = mapper.transformarUltimosDatosArduino(llenarUltimosDatosArduinoDTO())
        var esperado = listaDatosCalidadAireUltimos()

        assertEquals(esperado, obtenido)
    }

    private fun listaDatosCalidadAireUltimos(): List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        lista.add(DatosCalidadAire("Spain", "Sevilla, España", "2020-04-01T00:00:00.000Z", hashMapOf("so2" to 15.0, "o3" to 30.7)))
        lista.add(DatosCalidadAire("Greece", "Chania, Greece", "2020-04-01T00:00:00.000Z", hashMapOf("so2" to 18.4, "o3" to 25.0)))
        lista.add(DatosCalidadAire("Bulgaria", "Sofía, Bulgaria", "2020-04-01T00:00:00.000Z", hashMapOf("so2" to 16.8, "o3" to 37.8)))

        return lista
    }

    private fun llenarUltimosDatosArduinoDTO(): DatosArduinoUltimosDTO {
        var spain = mutableListOf<DatosArduinoDTO>()
        var greece = mutableListOf<DatosArduinoDTO>()
        var bulgaria = mutableListOf<DatosArduinoDTO>()

        spain.add(DatosArduinoDTO("Spain", "2020-04-01T00:00:00.000Z", "so2", 15.0))
        spain.add(DatosArduinoDTO("Spain", "2020-04-01T00:00:00.000Z", "o3", 30.7))
        greece.add(DatosArduinoDTO("Greece", "2020-04-01T00:00:00.000Z", "so2", 18.4))
        greece.add(DatosArduinoDTO("Greece", "2020-04-01T00:00:00.000Z", "o3", 25.0))
        bulgaria.add(DatosArduinoDTO("Bulgaria", "2020-04-01T00:00:00.000Z", "so2", 16.8))
        bulgaria.add(DatosArduinoDTO("Bulgaria", "2020-04-01T00:00:00.000Z", "o3", 37.8))

        return DatosArduinoUltimosDTO(spain,greece,bulgaria)
    }




    private fun listaDatosCalidadAireDesdeArduino(): List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        lista.add(DatosCalidadAire("Spain", "Sevilla, España", "2020-04-01T00:00:00.000Z", hashMapOf("so2" to 15.0, "o3" to 30.7)))
        lista.add(DatosCalidadAire("Spain", "Sevilla, España", "2020-04-02T00:00:00.000Z", hashMapOf("so2" to 18.4, "o3" to 25.0)))
        lista.add(DatosCalidadAire("Spain", "Sevilla, España", "2020-04-03T00:00:00.000Z", hashMapOf("o3" to 37.8)))

        return lista
    }

    private fun llenarListaDatosArduinoDTO(): List<DatosArduinoDTO> {
        var lista = mutableListOf<DatosArduinoDTO>()

        lista.add(DatosArduinoDTO("Spain", "2020-04-01T00:00:00.000Z", "so2", 15.0))
        lista.add(DatosArduinoDTO("Spain", "2020-04-01T00:00:00.000Z", "o3", 30.7))
        lista.add(DatosArduinoDTO("Spain", "2020-04-02T00:00:00.000Z", "so2", 18.4))
        lista.add(DatosArduinoDTO("Spain", "2020-04-02T00:00:00.000Z", "o3", 25.0))
        lista.add(DatosArduinoDTO("Spain", "2020-04-03T00:00:00.000Z", "o3", 37.8))

        return lista

    }

    private fun listaDatosCalidadAire(): List<DatosCalidadAire> {
        var lista = mutableListOf<DatosCalidadAire>()

        lista.add(DatosCalidadAire("8495", "Sevilla, España", "2020-04-01T00:00:00.000Z", hashMapOf("so2" to 15.0, "o3" to 30.7)))
        lista.add(DatosCalidadAire("8495", "Sevilla, España", "2020-04-02T00:00:00.000Z", hashMapOf("so2" to 18.4, "o3" to 25.0)))
        lista.add(DatosCalidadAire("8495", "Sevilla, España", "2020-04-03T00:00:00.000Z", hashMapOf("o3" to 37.8)))

        return lista

    }

    private fun llenarDatosHistoricosDTO(): List<DatosHistoricosDTO> {
        var lista = mutableListOf<DatosHistoricosDTO>()

        lista.add(DatosHistoricosDTO(8495, "2020-04-01T00:00:00.000Z", "so2", 15.0))
        lista.add(DatosHistoricosDTO(8495, "2020-04-01T00:00:00.000Z", "o3", 30.7))
        lista.add(DatosHistoricosDTO(8495, "2020-04-02T00:00:00.000Z", "so2", 18.4))
        lista.add(DatosHistoricosDTO(8495, "2020-04-02T00:00:00.000Z", "o3", 25.0))
        lista.add(DatosHistoricosDTO(8495, "2020-04-03T00:00:00.000Z", "o3", 37.8))

        return lista
    }


}