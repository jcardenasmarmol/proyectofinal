package com.cardenas.jesus.proyectofinal.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.CiudadWAQI
import com.cardenas.jesus.proyectofinal.model.DatosCiudadesWAQI
import com.cardenas.jesus.proyectofinal.tasks.GetWAQIEstaciones
import com.cardenas.jesus.proyectofinal.view.MySearchView
import com.cardenas.jesus.proyectofinal.view.adapters.AdapterDatosCiudades
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), MySearchView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchBtn = view.findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener {
            loadData()
            val imm: InputMethodManager? =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(search_box.windowToken, 0)
        }

        return view
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

        val serviceJob = GetWAQIEstaciones(Params(50).requireNetwork(), this, search_box.text.toString())
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: DatosCiudadesWAQI) {
        mainRecyclerView?.layoutManager = LinearLayoutManager(this.context)
        mainRecyclerView?.adapter = AdapterDatosCiudades(result.ciudades){
            agregar(it)
        }
        mainRecyclerView?.visibility = View.VISIBLE

    }

    private fun agregar(ciudad : CiudadWAQI){
        val preferences = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val estaciones = mutableSetOf<String>()
        preferences.let {
            it.getStringSet("estaciones", null)?.map {
                estaciones.add(it)
            }
        }
        if (!estaciones.contains("@"+ciudad.id.toString())) estaciones?.add("@"+ciudad.id.toString())
        else Toast.makeText(context, "Ya esta agregada", Toast.LENGTH_LONG)
        val edit = preferences.edit()
        edit.putStringSet("estaciones", estaciones)
        edit.apply()
    }

}
