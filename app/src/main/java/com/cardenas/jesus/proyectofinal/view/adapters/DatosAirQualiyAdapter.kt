package com.cardenas.jesus.proyectofinal.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import kotlinx.android.synthetic.main.row.view.*

class DatosAirQualiyAdapter (val data : List<DatosAirQualityModel?>) : RecyclerView.Adapter<DatosAirQualiyAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val fecha = itemView.fecha
        val nombreEstacion = itemView.nombreEstacion
        val dataCO = itemView.dataCO
        val contenedorCO = itemView.contenedorCO
        val dataNO2 = itemView.dataNO2
        val contenedorNO2 = itemView.contenedorNO2
        val dataO3 = itemView.dataO3
        val contenedorO3 = itemView.contenedorO3
        val dataPM10 = itemView.dataPM10
        val contenedorPM10 = itemView.contenedorPM10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.data[position]

        holder.idEstacion.text = data?.id
        holder.nombreEstacion.text = data?.city
        holder.fecha.text = data?.fecha

        data?.contaminantes?.get("no2")?.let {
            holder.contenedorNO2.visibility = View.VISIBLE
            holder.dataNO2.text = "NO2 | $it"
        }
        data?.contaminantes?.get("co")?.let {
            holder.contenedorCO.visibility = View.VISIBLE
            holder.dataCO.text = "CO | $it"
        }
        data?.contaminantes?.get("o3")?.let {
            holder.contenedorO3.visibility = View.VISIBLE
            holder.dataO3.text = "O3 | $it"
        }
        data?.contaminantes?.get("pm10")?.let {
            holder.contenedorPM10.visibility = View.VISIBLE
            holder.dataPM10.text = "PM10 | $it"
        }
    }
}
/**
 *      Datos dispositivo arduino
        CO2
        NO
        NH3
        CO
        PM10
        PM25
 */