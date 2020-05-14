package com.cardenas.jesus.proyectofinal.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.CiudadWAQI
import com.cardenas.jesus.proyectofinal.model.DatosCiudadesWAQI
import com.cardenas.jesus.proyectofinal.ui.adapters.AdapterDatosCiudades
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : AdapterDatosCiudades
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchBtn = view.findViewById<ImageButton>(R.id.search_btn)
        recyclerView = view.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter =
            AdapterDatosCiudades(
                mutableListOf()
            ) {
                agregar(it)
            }
        recyclerView.adapter = adapter

        searchViewModel = SearchViewModel.SearchViewModelFactory()
            .create(SearchViewModel::class.java)

        searchBtn.setOnClickListener {
            val imm: InputMethodManager? =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(search_box.windowToken, 0)
            searchViewModel.GetDatos(search_box.text.toString())
                .observe(viewLifecycleOwner, Observer {
                    actualizar(it)
                })

        }

        return view
    }

    private fun actualizar(it: DatosCiudadesWAQI) {
        adapter.datos.clear()
        adapter.datos.addAll(it.ciudades)
        adapter.notifyDataSetChanged()
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
