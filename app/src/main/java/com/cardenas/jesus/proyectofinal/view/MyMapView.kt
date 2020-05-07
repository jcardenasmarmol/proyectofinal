package com.cardenas.jesus.proyectofinal.view

import com.cardenas.jesus.proyectofinal.model.DatosDispositivoPortable
import com.google.android.gms.maps.GoogleMap

interface MyMapView {
    fun setDataSet(
        result: List<DatosDispositivoPortable?>,
        googleMap: GoogleMap
    )
}
