package com.cardenas.jesus.proyectofinal.ui.consultas

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.DatePickerFragment
import com.cardenas.jesus.proyectofinal.R

class ConsultaFragment : Fragment() {

    private lateinit var consultaViewModel: ConsultaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        consultaViewModel = ConsultaViewModel.ConsultaViewModelFactory().create(ConsultaViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_consulta, container, false)
        val button = view.findViewById<Button>(R.id.button)
        val estacion = view.findViewById<Spinner>(R.id.estacion)
        val fechaInicial = view.findViewById<TextView>(R.id.fechaInicial)
        consultaViewModel.fechaInicial.observe(viewLifecycleOwner, Observer {
            fechaInicial.text = it
        })
        val fechaFinal = view.findViewById<TextView>(R.id.fechaFinal)
        consultaViewModel.fechaFinal.observe(viewLifecycleOwner, Observer {
            fechaFinal.text = it
        })


        val spinner: Spinner = view.findViewById(R.id.estacion)
        consultaViewModel.getEstaciones(arguments?.getBoolean("consultaArduino")?:false)
            .observe(viewLifecycleOwner, Observer {
                val adapter = ArrayAdapter(context!!,
                    android.R.layout.simple_spinner_item,
                    it
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            })

        fechaInicial.setOnClickListener {
            val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val dayStr = day.twoDigits()
                val monthStr = (month + 1).twoDigits() // +1 because January is zero

                fechaInicial.text = "$year-$monthStr-$dayStr"
            })
            newFragment.show(parentFragmentManager, "datePicker")
        }
        fechaFinal.setOnClickListener{
            val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val dayStr = day.twoDigits()
                val monthStr = (month + 1).twoDigits() // +1 because January is zero

                fechaFinal.text = "$year-$monthStr-$dayStr"
            })
            newFragment.show(parentFragmentManager, "datePicker")
        }


        button.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("consultaArduino", arguments?.getBoolean("consultaArduino")?:false)
            if (arguments?.getBoolean("consultaArduino") == true)
                bundle.putString("estacion", estacion.selectedItem as String)
            else
                bundle.putInt("estacion", when {
                    (estacion.selectedItem as String).contains(AppConstants.SEVILLACODE) -> 8495
                    (estacion.selectedItem as String).contains(AppConstants.GREECECODE) -> 12410
                    (estacion.selectedItem as String).contains(AppConstants.SOFIACODE) -> 8084
                    else -> 0
                })
            bundle.putString("fechaInicial", fechaInicial.text.toString())
            bundle.putString("fechaFinal", fechaFinal.text.toString())
            findNavController().navigate(R.id.action_go_graphFragment, bundle)
        }

        return view
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            onCreate(null)
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            onCreate(null)
        }
    }
    fun Int.twoDigits() =
        if (this <= 9) "0$this" else this.toString()
}

