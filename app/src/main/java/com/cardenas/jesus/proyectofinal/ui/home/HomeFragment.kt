package com.cardenas.jesus.proyectofinal.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.ui.adapters.NewAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    var estaciones = mutableListOf<String>()

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : NewAdapter
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter =
            NewAdapter(mutableListOf())
        recyclerView.adapter = adapter
        homeViewModel = HomeViewModel.HomeViewModelFactory().create(HomeViewModel::class.java)

        setHasOptionsMenu(true)
        val preferences = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        cargarPreferencias(preferences)

        homeViewModel.GetDatos(estaciones).map {
            it.observe(viewLifecycleOwner, Observer { dato -> actualizar(dato) })
        }

        return view
    }

    private fun actualizar(it : DatosAirQualityModel){
        adapter.data.add(it)
        adapter.notifyDataSetChanged()
        if (loadingView.isVisible){
            loadingView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add -> agregarEstacion()
            R.id.action_edit -> mainRecyclerView.adapter?.notifyDataSetChanged() //Toast.makeText(context, "Editar", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun agregarEstacion() {
        findNavController().navigate(R.id.action_go_to_search)
    }

}
