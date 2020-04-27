package com.cardenas.jesus.proyectofinal.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetHistoricosPorFecha
import com.cardenas.jesus.proyectofinal.view.MainView
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import kotlinx.android.synthetic.main.fragment_graph.*
import java.util.*

class GraphFragment : Fragment(), MainView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        loadData()
        return view
    }

    private fun loadData() {
        val builder = this.context?.let {
            Configuration.Builder(it)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(2)
                .consumerKeepAlive(200)
        }


        val jobManager = JobManager(builder?.build())

        val serviceJob = GetHistoricosPorFecha(
            Params(50).requireNetwork(),
            this, arguments?.getInt("estacion") ?: 8495,
            arguments?.getString("fechaInicial") ?: "2020-01-01",
            arguments?.getString("fechaFinal") ?: "2020-01-15"
        )
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: List<DatosAirQualityModel?>) {

        chart.setViewPortOffsets(0f, 0f, 0f, 0f)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true)

        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 300f

        val x = chart.xAxis
        x.isEnabled = false

        val y = chart.axisLeft
        y.setLabelCount(6, false)
        y.textColor = Color.BLACK
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE

        chart.axisRight.isEnabled = false

        // add data
        setData(result)

        chart.legend.isEnabled = false

        chart.animateXY(2000, 2000)

        loadingView.visibility = View.GONE
        chart.visibility = View.VISIBLE
        // don't forget to refresh the drawing
        chart.invalidate()

    }


    private fun setData(result: List<DatosAirQualityModel?>) {

        val valuesCO = ArrayList<Entry>()
        val valuesNO2 = ArrayList<Entry>()
        val valuesO3 = ArrayList<Entry>()

        var count = 1f
        result.map { data ->
            data?.contaminantes?.get("co").let {
                valuesCO.add(Entry(count, it?.toFloat() ?: 0f)) }
            data?.contaminantes?.get("no2").let {
                valuesNO2.add(Entry(count, it?.toFloat() ?: 0f)) }
            data?.contaminantes?.get("o3").let {
                valuesO3.add(Entry(count, it?.toFloat() ?: 0f)) }
            count++
        }

        val setCO = LineDataSet(valuesCO, "CO")
        setCO.mode = LineDataSet.Mode.CUBIC_BEZIER
        setCO.color = Color.GREEN
        val setNO2 = LineDataSet(valuesNO2, "NO2")
        setNO2.mode = LineDataSet.Mode.CUBIC_BEZIER
        setNO2.color = Color.CYAN
        val setO3 = LineDataSet(valuesO3, "O3")
        setO3.mode = LineDataSet.Mode.CUBIC_BEZIER
        setO3.color = Color.MAGENTA


        val data = LineData(listOf(setCO, setNO2, setO3))
        data.setValueTextSize(9f)
        data.setDrawValues(false)

        // set data
        chart.data = data

    }
}
/* create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillColor = Color.WHITE
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            */