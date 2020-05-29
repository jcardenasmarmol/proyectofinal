package com.cardenas.jesus.proyectofinal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.ui.adapters.DatosCalidadAireAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : DatosCalidadAireAdapter
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
            DatosCalidadAireAdapter(mutableListOf())
        recyclerView.adapter = adapter
        homeViewModel = HomeViewModel.HomeViewModelFactory().create(HomeViewModel::class.java)

        homeViewModel.GetDatos().map {
            it.observe(viewLifecycleOwner, Observer { dato -> actualizar(dato) })
        }

        return view
    }

    private fun actualizar(it : DatosCalidadAire){
        adapter.data.add(it)
        adapter.notifyDataSetChanged()
        if (loadingView.isVisible){
            loadingView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

}
