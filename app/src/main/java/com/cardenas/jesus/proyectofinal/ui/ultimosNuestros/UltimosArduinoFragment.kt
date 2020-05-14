package com.cardenas.jesus.proyectofinal.ui.ultimosNuestros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.ui.adapters.DatosAirQualiyAdapter
import kotlinx.android.synthetic.main.fragment_ultimos_arduino.*

class UltimosArduinoFragment : Fragment() {

    private lateinit var ultimosNuestrosViewModel: UltimosNuestrosViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ultimosNuestrosViewModel = UltimosNuestrosViewModel.UltimosNuestrosViewModelFactory().create(UltimosNuestrosViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_ultimos_arduino, container, false)

        ultimosNuestrosViewModel.getUltimos().observe(viewLifecycleOwner, Observer {
            setDataSet(it)
        })

        return view
    }

    fun setDataSet(result: List<DatosAirQualityModel?>) {
        mainRecyclerView?.layoutManager = LinearLayoutManager(this.context)
        mainRecyclerView?.adapter =
            DatosAirQualiyAdapter(
                result
            )
        loadingView?.visibility = View.GONE
        mainRecyclerView?.visibility = View.VISIBLE
    }
}
