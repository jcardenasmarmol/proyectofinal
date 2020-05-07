package com.cardenas.jesus.proyectofinal.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.DatePickerFragment

class ConsultaHistoricosFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_consulta_historicos, container, false)
        val button = view.findViewById<Button>(R.id.button)
        val estacion = view.findViewById<Spinner>(R.id.estacion)
        val fechaInicial = view.findViewById<TextView>(R.id.fechaInicial)
        val fechaFinal = view.findViewById<TextView>(R.id.fechaFinal)

        val spinner: Spinner = view.findViewById(R.id.estacion)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context!!,
            R.array.estaciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

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
        val idEstacion = when {
            (estacion.selectedItem as String).contains(AppConstants.SEVILLACODE) -> 8495
            (estacion.selectedItem as String).contains(AppConstants.GREECECODE) -> 12410
            (estacion.selectedItem as String).contains(AppConstants.SOFIACODE) -> 8084
            else -> 0
        }
        button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("estacion", idEstacion)
            bundle.putString("fechaInicial", fechaInicial.text.toString())
            bundle.putString("fechaFinal", fechaFinal.text.toString())
            findNavController().navigate(R.id.action_go_graphFragment, bundle)
        }

        return view
    }

}
fun Int.twoDigits() =
    if (this <= 9) "0$this" else this.toString()


