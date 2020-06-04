package com.cardenas.jesus.proyectofinal.ui.consultas

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.utilidades.AppConstants
import com.cardenas.jesus.proyectofinal.utilidades.DatePickerFragment
import com.cardenas.jesus.proyectofinal.utilidades.DialogoAlerta
import com.cardenas.jesus.proyectofinal.utilidades.twoDigits
import kotlinx.android.synthetic.main.fragment_consulta.*

class ConsultaFragment : Fragment() {

    private lateinit var consultaViewModel: ConsultaViewModel
    private lateinit var btnConsultar: Button
    private lateinit var fechaInicial: EditText
    private lateinit var fechaFinal: EditText
    private lateinit var spinnerEstaciones: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        consultaViewModel =
            ConsultaViewModel.ConsultaViewModelFactory().create(ConsultaViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_consulta, container, false)
        setUI(view)

        consultaViewModel.fechaInicial.observe(viewLifecycleOwner, Observer {
            fechaInicial.setText(it)
        })

        consultaViewModel.fechaFinal.observe(viewLifecycleOwner, Observer {
            fechaFinal.setText(it)
        })

        consultaViewModel.getEstaciones(arguments?.getBoolean("consultaArduino") ?: false)
            .observe(viewLifecycleOwner, Observer {
                val adapter = ArrayAdapter(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    it
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEstaciones.adapter = adapter
            })

        return view
    }

    private fun setUI(view: View) {
        btnConsultar = view.findViewById<Button>(R.id.button)
        fechaInicial = view.findViewById<EditText>(R.id.fechaInicial)
        fechaFinal = view.findViewById<EditText>(R.id.fechaFinal)
        spinnerEstaciones = view.findViewById(R.id.estacion)
        fechaInicial.setOnClickListener {
            mostrarDatePickerFragment(fechaInicial)
        }
        fechaFinal.setOnClickListener {
            mostrarDatePickerFragment(fechaFinal)
        }
        btnConsultar.setOnClickListener {
            if (comprobarRangoFechas())
                irAGraphFragment()
            else
                DialogoAlerta("Rango de fechas no válido").show(parentFragmentManager, "Error")
        }
    }

    private fun comprobarRangoFechas(): Boolean {
        return fechaInicial.text.toString() < fechaFinal.text.toString()
    }

    private fun irAGraphFragment() {
        val bundle = Bundle()
        bundle.putBoolean("consultaArduino", arguments?.getBoolean("consultaArduino") ?: false)
        if (arguments?.getBoolean("consultaArduino") == true)
            bundle.putString("estacion", estacion.selectedItem as String)
        else
            bundle.putInt(
                "estacion", when {
                    (estacion.selectedItem as String).contains(AppConstants.SEVILLACODE) -> 8495
                    (estacion.selectedItem as String).contains(AppConstants.GREECECODE) -> 12410
                    (estacion.selectedItem as String).contains(AppConstants.SOFIACODE) -> 8084
                    else -> 0
                }
            )
        bundle.putString("fechaInicial", fechaInicial.text.toString())
        bundle.putString("fechaFinal", fechaFinal.text.toString())
        findNavController().navigate(R.id.action_go_graphFragment, bundle)
    }

    private fun mostrarDatePickerFragment(textView: TextView?) {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val dayStr = day.twoDigits()
                val monthStr = (month + 1).twoDigits() // +1 because January is zero

                textView?.text = "$year-$monthStr-$dayStr"
            })
        newFragment.show(parentFragmentManager, "datePicker")
    }

}

