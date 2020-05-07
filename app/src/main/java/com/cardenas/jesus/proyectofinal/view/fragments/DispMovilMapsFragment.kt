package com.cardenas.jesus.proyectofinal.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosDispositivoPortable
import com.cardenas.jesus.proyectofinal.tasks.GetDatosDispPortables
import com.cardenas.jesus.proyectofinal.view.MyMapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class DispMovilMapsFragment : Fragment(), MyMapView {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         * val sydney = LatLng(37.3828300, -5.9731700)
         * googleMap.addMarker(MarkerOptions().position(sydney).title("Sevilla"))
         * googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 6f))
         */
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(36.0,-5.0), 5F))
        loadData(googleMap)
        googleMap.setInfoWindowAdapter(PopUp(layoutInflater))
        googleMap.setOnMarkerClickListener {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.position, 20F))
            it.showInfoWindow()
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disp_movil_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun loadData(googleMap: GoogleMap) {
        val builder = this.context?.let {
            Configuration.Builder(it)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(2)
                .consumerKeepAlive(500)
        }

        val jobManager = JobManager(builder?.build())

        val serviceJob = GetDatosDispPortables(Params(50).requireNetwork(), this, googleMap)
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(
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