package com.cardenas.jesus.proyectofinal.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetWAQI
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.view.adapters.DatosAirQualiyAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), MainView {

    var estaciones = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        val preferences = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        cargarPreferencias(preferences)
        loadData()
        return view
    }

    private fun cargarPreferencias(preferences: SharedPreferences) {
        preferences.let {
            it.getStringSet("estaciones", null)?.forEach { estacion ->
                if (!estaciones.contains(estacion)) estaciones.add(estacion)
            }
        } ?: cargar(preferences)
    }

    private fun cargar(preferences: SharedPreferences) {
        estaciones.add(AppConstants.SEVILLACODE)
        estaciones.add(AppConstants.SOFIACODE)
        estaciones.add(AppConstants.GREECECODE)
        val edit = preferences.edit()
        edit.putStringSet(
            "estaciones",
            mutableSetOf(AppConstants.SEVILLACODE, AppConstants.SOFIACODE, AppConstants.GREECECODE)
        )
        edit.apply()
    }

    private fun loadData() {
        val builder = this.context?.let {
            Configuration.Builder(it)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(2)
                .consumerKeepAlive(200)
        }

        val jobManager = JobManager(builder?.build())

        val serviceJob = GetWAQI(Params(50).requireNetwork(), this, estaciones)
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: List<DatosAirQualityModel?>) {
        mainRecyclerView?.layoutManager = LinearLayoutManager(this.context)
        mainRecyclerView?.adapter = DatosAirQualiyAdapter(result)
        loadingView?.visibility = View.GONE
        mainRecyclerView?.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add -> agregarEstacion()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun agregarEstacion() {
        findNavController().navigate(R.id.action_go_to_search)
    }

}
