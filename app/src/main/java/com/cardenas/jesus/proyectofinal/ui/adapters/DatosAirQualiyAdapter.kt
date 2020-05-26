package com.cardenas.jesus.proyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.utilidades.Utils
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.row_last.view.*

class DatosAirQualiyAdapter (val data : List<DatosAirQualityModel?>) : RecyclerView.Adapter<DatosAirQualiyAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val fecha = itemView.fecha
        val nombreEstacion = itemView.nombreEstacion
        val indice = itemView.indice
        val dataCO = itemView.dataCO
        val contenedorCO = itemView.contenedorCO
        val imagenCO = itemView.nivelCO
        val dataPM10 = itemView.dataPM10
        val contenedorPM10 = itemView.contenedorPM10
        val imagenPM10 = itemView.nivelPM10
        val dataCO2 = itemView.dataCO2
        val contenedorCO2 = itemView.contenedorCO2
        val imagenCO2 = itemView.nivelCO2
        val dataNO = itemView.dataNO
        val contenedorNO = itemView.contenedorNO
        val imagenNO = itemView.nivelNO
        val dataNH3 = itemView.dataNH3
        val contenedorNH3 = itemView.contenedorNH3
        val imagenNH3 = itemView.nivelNH3
        val dataPM25 = itemView.dataPM25
        val contenedorPM25 = itemView.contenedorPM25
        val imagenPM25 = itemView.nivelPM25
        val chart = itemView.chart
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_last, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.data[position]
        var valorIndice = 0.0
        var colorIndice:Int
        holder.idEstacion.text = data?.id
        holder.nombreEstacion.text = data?.city
        holder.fecha.text = data?.fecha?.substring(IntRange(0,9))


        val contaminantes = mutableListOf<String>()
        val datos = mutableListOf<Double>()

        data?.contaminantes?.get("co")?.let {
            holder.contenedorCO.visibility = View.VISIBLE
            holder.dataCO.text = "$it"
            if (it > valorIndice) valorIndice = it
            if (it<10) holder.imagenCO.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else holder.imagenCO.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("CO")
            datos.add(it)
        }
        data?.contaminantes?.get("pm10")?.let {
            holder.contenedorPM10.visibility = View.VISIBLE
            holder.dataPM10.text = "$it"
            if (it > valorIndice) valorIndice = it
            if (it<35) holder.imagenPM10.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=50) holder.imagenPM10.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenPM10.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("PM10")
            datos.add(it)
        }
        data?.contaminantes?.get("co2")?.let {
            holder.contenedorCO2.visibility = View.VISIBLE
            holder.dataCO2.text = "$it"
            if (it > valorIndice) valorIndice = it
            contaminantes.add("CO2")
            datos.add(it)
        }
        data?.contaminantes?.get("no")?.let {
            holder.contenedorNO.visibility = View.VISIBLE
            holder.dataNO.text = "$it"
            if (it > valorIndice) valorIndice = it
            contaminantes.add("NO")
            datos.add(it)
        }
        data?.contaminantes?.get("nh3")?.let {
            holder.contenedorNH3.visibility = View.VISIBLE
            holder.dataNH3.text = "$it"
            if (it > valorIndice) valorIndice = it
            contaminantes.add("NH3")
            datos.add(it)
        }
        data?.contaminantes?.get("pm25")?.let {
            holder.contenedorPM25.visibility = View.VISIBLE
            holder.dataPM25.text = "$it"
            if (it > valorIndice) valorIndice = it
            if (it<20) holder.imagenPM25.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=25) holder.imagenPM25.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenPM25.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("PM25")
            datos.add(it)
        }

        holder.indice.text = valorIndice.toString()
        chart(holder.chart, contaminantes, datos)
    }

    private fun chart(
        chart: BarChart,
        nombres: MutableList<String>,
        datos: MutableList<Double>
    ) {
        Utils.llenar(chart, nombres, datos)

        chart.animateY(1500)
    }
}
