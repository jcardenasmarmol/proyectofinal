package com.cardenas.jesus.proyectofinal.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosDispositivoPortable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class DispMovilMapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var dispMovilMapsViewModel: DispMovilMapsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dispMovilMapsViewModel = DispMovilMapsViewModel.DispMovilMapsViewModelFactory().create(DispMovilMapsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_disp_movil_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    fun setDataSet(
        result: List<DatosDispositivoPortable?>,
        googleMap: GoogleMap
    ) {
        result.map { dato ->
            dato?.let {
                var snippet = "Fecha: " +
                        it.fecha + "\n" +
                        "Datos:\n"
                it?.contaminantes?.get("no")?.let { v -> snippet += "NO $v\n" }
                it?.contaminantes?.get("nh3")?.let { v -> snippet += "NH3 $v\n" }
                it?.contaminantes?.get("co2")?.let { v -> snippet += "CO2 $v" }
                var marker = MarkerOptions().position(LatLng(it.latitud, it.longitud)).title(it.id)
                    .snippet(snippet)
                googleMap.addMarker(marker)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(36.0,-5.0), 5F))

        dispMovilMapsViewModel.getDatos().observe(viewLifecycleOwner, Observer {
            setDataSet(it, googleMap)
        })

        googleMap.setInfoWindowAdapter(
            PopUp(
                layoutInflater
            )
        )
        googleMap.setOnMarkerClickListener {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.position, 20F))
            it.showInfoWindow()
            true
        }
    }
}

class PopUp(
    val inflater: LayoutInflater
) : GoogleMap.InfoWindowAdapter {
    lateinit var popup: View

    override fun getInfoContents(p0: Marker?): View {

        popup = inflater.inflate(R.layout.popup, null)

        var tv = popup.findViewById<TextView>(R.id.title)

        tv.text = p0?.title
        tv = popup.findViewById(R.id.snippet)
        tv.text = p0?.snippet

        return popup
    }

    override fun getInfoWindow(p0: Marker?): View {
        return getInfoContents(p0)
    }
}