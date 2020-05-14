package com.cardenas.jesus.proyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.Utils
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import kotlinx.android.synthetic.main.row_last.view.*

class NewAdapter (
    val data : MutableList<DatosAirQualityModel?>
) : RecyclerView.Adapter<NewAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val fecha = itemView.fecha
        val nombreEstacion = itemView.nombreEstacion
        val indice = itemView.indice
        val dataCO = itemView.dataCO
        val contenedorCO = itemView.contenedorCO
        val imagenCO = itemView.nivelCO
        val dataNO2 = itemView.dataNO2
        val contenedorNO2 = itemView.contenedorNO2
        val imagenNO2 = itemView.nivelNO2
        val dataO3 = itemView.dataO3
        val contenedorO3 = itemView.contenedorO3
        val imagenO3 = itemView.nivelO3
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
        val dataSO2 = itemView.dataSO2
        val contenedorSO2 = itemView.contenedorSO2
        val imagenSO2 = itemView.nivelSO2
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
        holder.fecha.text = data?.fecha
        val contaminantes = mutableListOf<String>()
        val datos = mutableListOf<Double>()

        data?.contaminantes?.get("no2")?.let {
            holder.contenedorNO2.visibility = View.VISIBLE
            holder.dataNO2.text = "NO2 $it"
            if (it > valorIndice) valorIndice = it
            if (it<30) holder.imagenNO2.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=40) holder.imagenNO2.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenNO2.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("NO2")
            datos.add(it)
        }
        data?.contaminantes?.get("co")?.let {
            holder.contenedorCO.visibility = View.VISIBLE
            holder.dataCO.text = "CO $it"
            if (it > valorIndice) valorIndice = it
            contaminantes.add("CO")
            datos.add(it)
        }
        data?.contaminantes?.get("o3")?.let {
            holder.contenedorO3.visibility = View.VISIBLE
            holder.dataO3.text = "O3 $it"
            if (it > valorIndice) valorIndice = it
            if (it<75) holder.imagenO3.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=100) holder.imagenO3.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenO3.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("O3")
            datos.add(it)
        }
        data?.contaminantes?.get("pm10")?.let {
            holder.contenedorPM10.visibility = View.VISIBLE
            holder.dataPM10.text = "PM10 $it"
            if (it > valorIndice) valorIndice = it
            if (it<35) holder.imagenPM10.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=50) holder.imagenPM10.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenPM10.setImageResource(R.drawable.ic_nivel_malo_24dp)
            contaminantes.add("PM10")
            datos.add(it)
        }
        data?.contaminantes?.get("co2")?.let {
            holder.contenedorCO2.visibility = View.VISIBLE
            holder.dataCO2.text = "CO2 $it"
            if (it > valorIndice) valorIndice = it
        }
        data?.contaminantes?.get("no")?.let {
            holder.contenedorNO.visibility = View.VISIBLE
            holder.dataNO.text = "NO $it"
            if (it > valorIndice) valorIndice = it
        }
        data?.contaminantes?.get("nh3")?.let {
            holder.contenedorNH3.visibility = View.VISIBLE
            holder.dataNH3.text = "NH3 $it"
            if (it > valorIndice) valorIndice = it
        }
        data?.contaminantes?.get("pm25")?.let {
            holder.contenedorPM25.visibility = View.VISIBLE
            holder.dataPM25.text = "PM25 $it"
            if (it > valorIndice) valorIndice = it
            if (it<20) holder.imagenPM25.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=25) holder.imagenPM25.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenPM25.setImageResource(R.drawable.ic_nivel_malo_24dp)
        }
        data?.contaminantes?.get("so2")?.let {
            holder.contenedorSO2.visibility = View.VISIBLE
            holder.dataSO2.text = "SO2 $it"
            if (it > valorIndice) valorIndice = it
            if (it<15) holder.imagenSO2.setImageResource(R.drawable.ic_nivel_bueno_24dp)
            else if(it<=20) holder.imagenSO2.setImageResource(R.drawable.ic_nivel_regular_24dp)
            else holder.imagenSO2.setImageResource(R.drawable.ic_nivel_malo_24dp)
        }
        holder.indice.text = valorIndice.toString()
        chart(holder.chart, contaminantes, datos)

    }

    private fun chart(
        chart: BarChart,
        nombres: MutableList<String>,
        datos: MutableList<Double>
    ) {

        var chart = chart

        chart.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)

        val xAxis: XAxis = chart.xAxis
        xAxis.setDrawLabels(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        chart.axisLeft.setDrawGridLines(false)

        // setting data
        Utils.llenar(chart, nombres, datos)

        // add a nice and smooth animation
        chart.animateY(1500)

        chart.legend.isEnabled = true
    }
}
