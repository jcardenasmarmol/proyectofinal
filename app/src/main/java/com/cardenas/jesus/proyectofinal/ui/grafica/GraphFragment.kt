package com.cardenas.jesus.proyectofinal.ui.grafica

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_graph.*

class GraphFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    lateinit var datos : List<DatosAirQualityModel?>
    lateinit var setCO : LineDataSet
    lateinit var setNO2 : LineDataSet
    lateinit var setO3 : LineDataSet
    lateinit var setSO2 : LineDataSet
    lateinit var setPM25 : LineDataSet
    lateinit var setPM10 : LineDataSet
    lateinit var setNO : LineDataSet
    lateinit var setNH3 : LineDataSet
    lateinit var setCO2 : LineDataSet

    private lateinit var graphViewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        graphViewModel = GraphViewModel.GraphViewModelFactory().create(GraphViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_graph, container, false)

        val consultaArduino = arguments?.getBoolean("consultaArduino") ?: false
        if (consultaArduino){
            graphViewModel.getDatosArduino(arguments?.getString("estacion") ?: "Spain",
                arguments?.getString("fechaInicial") ?: "2020-01-01",
                arguments?.getString("fechaFinal") ?: "2020-01-15")
                .observe(viewLifecycleOwner, Observer {
                    setDataSet(it)
                    chart.notifyDataSetChanged()
                    setUI(view)
                })
        } else {
            graphViewModel.getDatosOficiales(arguments?.getInt("estacion") ?: 8495,
                arguments?.getString("fechaInicial") ?: "2020-01-01",
                arguments?.getString("fechaFinal") ?: "2020-01-15")
                .observe(viewLifecycleOwner, Observer {
                    setDataSet(it)
                    chart.notifyDataSetChanged()
                    setUI(view)
                })
        }
        return view
    }
    private fun setUI(view : View) {
        val switchCO = view.findViewById<Switch>(R.id.switchCO)
        switchCO.setOnCheckedChangeListener(this)
        val switchNO2 = view.findViewById<Switch>(R.id.switchNO2)
        switchNO2.setOnCheckedChangeListener(this)
        val switchO3 = view.findViewById<Switch>(R.id.switchO3)
        switchO3.setOnCheckedChangeListener(this)
        val switchSO2 = view.findViewById<Switch>(R.id.switchSO2)
        switchSO2.setOnCheckedChangeListener(this)
        val switchPM25 = view.findViewById<Switch>(R.id.switchPM25)
        switchPM25.setOnCheckedChangeListener(this)
        val switchPM10 = view.findViewById<Switch>(R.id.switchPM10)
        switchPM10.setOnCheckedChangeListener(this)
        val switcNO = view.findViewById<Switch>(R.id.switchNO)
        switcNO.setOnCheckedChangeListener(this)
        val switchNH3 = view.findViewById<Switch>(R.id.switchNH3)
        switchNH3.setOnCheckedChangeListener(this)
        val switchCO2 = view.findViewById<Switch>(R.id.switchCO2)
        switchCO2.setOnCheckedChangeListener(this)
    }

    fun setDataSet(result: List<DatosAirQualityModel?>) {
        datos = result
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

        chart.legend.isEnabled = true

        chart.animateXY(2000, 2000)

        loadingView.visibility = View.GONE
        chart.visibility = View.VISIBLE
        contenedor.visibility = View.VISIBLE
        // don't forget to refresh the drawing
        chart.invalidate()

    }


    private fun setData(result: List<DatosAirQualityModel?>) {

        val valuesCO = ArrayList<Entry>()
        val valuesNO2 = ArrayList<Entry>()
        val valuesO3 = ArrayList<Entry>()
        val valuesSO2 = ArrayList<Entry>()
        val valuesPM25 = ArrayList<Entry>()
        val valuesPM10 = ArrayList<Entry>()
        val valuesNO = ArrayList<Entry>()
        val valuesNH3 = ArrayList<Entry>()
        val valuesCO2 = ArrayList<Entry>()

        var count = 1f
        result.map { data ->
            data?.contaminantes?.get("co")?.let {
                valuesCO.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("no2")?.let {
                valuesNO2.add(Entry(count, it?.toFloat()))}
            data?.contaminantes?.get("o3")?.let {
                valuesO3.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("so2")?.let {
                valuesSO2.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("pm25")?.let {
                valuesPM25.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("pm10")?.let {
                valuesPM10.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("no")?.let {
                valuesNO.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("nh3")?.let {
                valuesNH3.add(Entry(count, it?.toFloat())) }
            data?.contaminantes?.get("co2")?.let {
                valuesCO2.add(Entry(count, it?.toFloat())) }
            count++
        }

        setCO = LineDataSet(valuesCO, "CO")
        setCO.mode = LineDataSet.Mode.CUBIC_BEZIER
        setCO.color = Color.GREEN
        setNO2 = LineDataSet(valuesNO2, "NO2")
        setNO2.mode = LineDataSet.Mode.CUBIC_BEZIER
        setNO2.color = Color.CYAN
        setO3 = LineDataSet(valuesO3, "O3")
        setO3.mode = LineDataSet.Mode.CUBIC_BEZIER
        setO3.color = Color.MAGENTA
        setSO2 = LineDataSet(valuesSO2, "SO2")
        setSO2.mode = LineDataSet.Mode.CUBIC_BEZIER
        setSO2.color = Color.BLACK
        setPM25 = LineDataSet(valuesPM25, "PM25")
        setPM25.mode = LineDataSet.Mode.CUBIC_BEZIER
        setPM25.color = Color.YELLOW
        setPM10 = LineDataSet(valuesPM10, "PM10")
        setPM10.mode = LineDataSet.Mode.CUBIC_BEZIER
        setPM10.color = Color.LTGRAY
        setNO = LineDataSet(valuesNO, "NO")
        setNO.mode = LineDataSet.Mode.CUBIC_BEZIER
        setNO.color = Color.RED
        setNH3 = LineDataSet(valuesNH3, "NH3")
        setNH3.mode = LineDataSet.Mode.CUBIC_BEZIER
        setNH3.color = Color.DKGRAY
        setCO2 = LineDataSet(valuesCO2, "CO2")
        setCO2.mode = LineDataSet.Mode.CUBIC_BEZIER
        setCO2.color = Color.BLUE


        var lista = mutableListOf<ILineDataSet>()


        val data = LineData(lista)
        data.setValueTextSize(9f)
        data.setDrawValues(false)

        // set data
        chart.data = data

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView != null) {
            when(buttonView.id){
                R.id.switchCO -> actualizaCO(isChecked)
                R.id.switchNO2 -> actualizaNO2(isChecked)
                R.id.switchO3 -> actualizaO3(isChecked)
                R.id.switchPM10 -> actualizaPM10(isChecked)
                R.id.switchPM25 -> actualizaPM25(isChecked)
                R.id.switchSO2 -> actualizaSO2(isChecked)
                R.id.switchNO -> actualizaNO(isChecked)
                R.id.switchNH3 -> actualizaNH3(isChecked)
                R.id.switchCO2 -> actualizaCO2(isChecked)
            }
        }

    }

    private fun actualizaCO2(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setCO2)
        else
            chart.data.removeDataSet(setCO2)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaNH3(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setNH3)
        else
            chart.data.removeDataSet(setNH3)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaNO(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setNO)
        else
            chart.data.removeDataSet(setNO)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaSO2(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setSO2)
        else
            chart.data.removeDataSet(setSO2)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaPM25(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setPM25)
        else
            chart.data.removeDataSet(setPM25)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaPM10(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setPM10)
        else
            chart.data.removeDataSet(setPM10)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaO3(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setO3)
        else
            chart.data.removeDataSet(setO3)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaNO2(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setNO2)
        else
            chart.data.removeDataSet(setNO2)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun actualizaCO(checked: Boolean) {
        if (checked)
            chart.data.addDataSet(setCO)
        else
            chart.data.removeDataSet(setCO)

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation === android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show()
            Thread.sleep(500)
        } else if (newConfig.orientation === android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show()
            Thread.sleep(500)
        }
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