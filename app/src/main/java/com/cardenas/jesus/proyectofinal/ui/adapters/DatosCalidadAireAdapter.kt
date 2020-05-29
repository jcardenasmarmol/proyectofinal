package com.cardenas.jesus.proyectofinal.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.row_last.view.*
import java.util.*

class DatosCalidadAireAdapter (
    val data : MutableList<DatosCalidadAire>
) : RecyclerView.Adapter<DatosCalidadAireAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val fecha = itemView.fecha
        val nombreEstacion = itemView.nombreEstacion
        val indice = itemView.indice
        val imagenIndice = itemView.indiceColor
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
        var colorIndice = R.drawable.ic_nivel_bueno_24dp

        holder.idEstacion.text = data.id
        holder.nombreEstacion.text = data.city
        holder.fecha.text = data.fecha

        val contaminantes = hashMapOf<String, Double>()

        data.contaminantes["no2"]?.let {
            holder.contenedorNO2.visibility = View.VISIBLE
            holder.dataNO2.text = "$it"
            var color : Int
            if (it<30) color = R.drawable.ic_nivel_bueno_24dp
            else if(it<=40) color = R.drawable.ic_nivel_regular_24dp
            else color = R.drawable.ic_nivel_malo_24dp

            if (it > valorIndice){ valorIndice = it; colorIndice = color}
            holder.imagenNO2.setImageResource(color)

            contaminantes.put("NO2",it)
        }
        data.contaminantes["co"]?.let {
            holder.contenedorCO.visibility = View.VISIBLE
            holder.dataCO.text = "$it"

            var color : Int
            if (it<10) color = R.drawable.ic_nivel_bueno_24dp
            else color = R.drawable.ic_nivel_malo_24dp
            holder.imagenCO.setImageResource(color)
            if (it > valorIndice) {valorIndice = it; colorIndice = color}

            contaminantes.put("CO",it)
        }
        data.contaminantes["o3"]?.let {
            holder.contenedorO3.visibility = View.VISIBLE
            holder.dataO3.text = "$it"
            var color : Int
            if (it<75) color = R.drawable.ic_nivel_bueno_24dp
            else if(it<=100) color = R.drawable.ic_nivel_regular_24dp
            else color = R.drawable.ic_nivel_malo_24dp
            holder.imagenO3.setImageResource(color)
            if (it > valorIndice) {valorIndice = it; colorIndice = color}

            contaminantes.put("O3",it)
        }
        data.contaminantes["pm10"]?.let {
            holder.contenedorPM10.visibility = View.VISIBLE
            holder.dataPM10.text = "$it"

            var color : Int
            if (it<35) color = R.drawable.ic_nivel_bueno_24dp
            else if(it<=50) color = R.drawable.ic_nivel_regular_24dp
            else color = R.drawable.ic_nivel_malo_24dp
            holder.imagenPM10.setImageResource(color)
            if (it > valorIndice) {valorIndice = it; colorIndice = color}

            contaminantes.put("PM10",it)
        }
        data.contaminantes["pm25"]?.let {
            holder.contenedorPM25.visibility = View.VISIBLE
            holder.dataPM25.text = "$it"

            var color : Int
            if (it<20) color = R.drawable.ic_nivel_bueno_24dp
            else if(it<=25) color = R.drawable.ic_nivel_regular_24dp
            else color = R.drawable.ic_nivel_malo_24dp
            holder.imagenPM25.setImageResource(color)
            if (it > valorIndice) {valorIndice = it; colorIndice = color}

            contaminantes.put("PM25",it)
        }
        data.contaminantes["so2"]?.let {
            holder.contenedorSO2.visibility = View.VISIBLE
            holder.dataSO2.text = "$it"

            var color : Int
            if (it<15) color = R.drawable.ic_nivel_bueno_24dp
            else if(it<=20) color = R.drawable.ic_nivel_regular_24dp
            else color = R.drawable.ic_nivel_malo_24dp
            holder.imagenSO2.setImageResource(color)
            if (it > valorIndice) {valorIndice = it; colorIndice = color}

            contaminantes.put("SO2",it)
        }
        data.contaminantes["co2"]?.let {
            holder.contenedorCO2.visibility = View.VISIBLE
            holder.dataCO2.text = "$it"
            if (it > valorIndice) valorIndice = it

            contaminantes.put("CO2",it)
        }
        data.contaminantes["no"]?.let {
            holder.contenedorNO.visibility = View.VISIBLE
            holder.dataNO.text = "$it"
            if (it > valorIndice) valorIndice = it

            contaminantes.put("NO",it)
        }

        holder.indice.text = valorIndice.toString()
        holder.imagenIndice.setImageResource(colorIndice)
        chart(holder.chart, contaminantes)

    }



    private fun chart(
        chart: BarChart,
        datos: HashMap<String,Double>
    ) {
        val dataSets = mutableListOf<IBarDataSet>()
        var i = 0f
        datos.map {
            val values = ArrayList<BarEntry>()
            values.add(BarEntry(i, it.value.toFloat()))
            var set1: BarDataSet
            set1 = BarDataSet(values, it.key)
            set1.color = darColor(it.key, it.value.toFloat())
            set1.setDrawValues(false)
            dataSets.add(set1)
            i += 1f
        }

        val data = BarData(dataSets)
        chart.data = data
        chart.legend.isEnabled = false
        chart.description.text = "μg/m3"
        chart.description.setPosition(150f, 50f)
        chart.description.isEnabled = true
        val xAxisFormatter: ValueFormatter =
            IndexAxisValueFormatter(datos.keys)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.valueFormatter = xAxisFormatter
        xAxis.setDrawLabels(true)
        val leftAxis = chart.axisLeft
        leftAxis.setDrawLabels(true)
        leftAxis.setDrawGridLines(true)
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f
        val rightAxis = chart.axisRight
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f
        chart.setFitBars(true)
        chart.invalidate()

        chart.animateY(1500)
    }

    private fun darColor(label: String?, valor: Float): Int {
        return when (label) {
            "NO2" -> colorNO2(valor)
            "PM10" -> colorPM10(valor)
            "O3" -> colorO3(valor)
            "SO2" -> colorSO2(valor)
            "PM25" -> colorPM25(valor)
            "CO" -> colorCO(valor)
            else -> Color.LTGRAY
        }
    }

    private fun colorCO(valor: Float): Int {
        return if (valor < 10) Color.GREEN else Color.RED
    }

    private fun colorNO2(valor: Float): Int {
        return if (valor < 30) Color.GREEN else if (valor <= 40) Color.YELLOW else Color.RED
    }

    private fun colorPM10(valor: Float): Int {
        return if (valor < 35) Color.GREEN else if (valor <= 50) Color.YELLOW else Color.RED
    }

    private fun colorO3(valor: Float): Int {
        return if (valor < 75) Color.GREEN else if (valor <= 100) Color.YELLOW else Color.RED
    }

    private fun colorPM25(valor: Float): Int {
        return if (valor < 20) Color.GREEN else if (valor <= 25) Color.YELLOW else Color.RED
    }

    private fun colorSO2(valor: Float): Int {
        return if (valor < 15) Color.GREEN else if (valor <= 20) Color.YELLOW else Color.RED
    }

}
