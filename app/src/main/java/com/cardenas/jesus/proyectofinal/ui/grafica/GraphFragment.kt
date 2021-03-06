package com.cardenas.jesus.proyectofinal.ui.grafica

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.modelo.DatosCalidadAire
import com.cardenas.jesus.proyectofinal.utilidades.setTitle
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_graph.*

class GraphFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private lateinit var setCO: LineDataSet
    private lateinit var setNO2: LineDataSet
    private lateinit var setO3: LineDataSet
    private lateinit var setSO2: LineDataSet
    private lateinit var setPM25: LineDataSet
    private lateinit var setPM10: LineDataSet
    private lateinit var setNO: LineDataSet
    private lateinit var setNH3: LineDataSet
    private lateinit var setCO2: LineDataSet
    private lateinit var graphViewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        graphViewModel = GraphViewModel.GraphViewModelFactory(parentFragmentManager)
            .create(GraphViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        var titulo: String
        val consultaArduino = arguments?.getBoolean("consultaArduino") ?: false
        if (consultaArduino) {
            titulo = when (arguments?.getString("estacion")) {
                "Spain" -> "Sevilla, España"
                "Greece" -> "Chania, Grecia"
                "Bulgaria" -> "Sofía, Bulgaria"
                else -> ""
            }
            graphViewModel.getDatosArduino(
                arguments?.getString("estacion") ?: "Spain",
                arguments?.getString("fechaInicial") ?: "2020-01-01",
                arguments?.getString("fechaFinal") ?: "2020-01-15"
            )
                .observe(viewLifecycleOwner, Observer {
                    setDataSet(it)
                })
        } else {
            titulo = when (arguments?.getInt("estacion")) {
                8495 -> "Bermejales, Sevilla, España"
                12410 -> "Patra-2, Grecia"
                8084 -> "Druzhba, Sofía, Bulgaria"
                else -> ""
            }
            graphViewModel.getDatosOficiales(
                arguments?.getInt("estacion") ?: 8495,
                arguments?.getString("fechaInicial") ?: "2020-01-01",
                arguments?.getString("fechaFinal") ?: "2020-01-15"
            )
                .observe(viewLifecycleOwner, Observer {
                    setDataSet(it)
                })
        }
        setTitle(titulo)
        return view
    }

    fun setDataSet(result: List<DatosCalidadAire?>) {
        crearGrafico(result)
        view?.let {
            setUI(it)
            actualizar()
        }
    }

    private fun actualizar() {
        actualizaCO(switchCO.isChecked)
        actualizaNO2(switchNO2.isChecked)
        actualizaO3(switchO3.isChecked)
        actualizaSO2(switchSO2.isChecked)
        actualizaPM25(switchPM25.isChecked)
        actualizaPM10(switchPM10.isChecked)
        actualizaNO(switchNO.isChecked)
        actualizaNH3(switchNH3.isChecked)
        actualizaCO2(switchCO2.isChecked)
    }

    private fun setUI(view: View) {
        val switchCO = view.findViewById<Switch>(R.id.switchCO)
        switchCO.setOnCheckedChangeListener(this)
        if (setCO.values.isNotEmpty()) switchCO.visibility = View.VISIBLE

        val switchNO2 = view.findViewById<Switch>(R.id.switchNO2)
        switchNO2.setOnCheckedChangeListener(this)
        if (setNO2.values.isNotEmpty()) switchNO2.visibility = View.VISIBLE

        val switchO3 = view.findViewById<Switch>(R.id.switchO3)
        switchO3.setOnCheckedChangeListener(this)
        if (setO3.values.isNotEmpty()) switchO3.visibility = View.VISIBLE

        val switchSO2 = view.findViewById<Switch>(R.id.switchSO2)
        switchSO2.setOnCheckedChangeListener(this)
        if (setSO2.values.isNotEmpty()) switchSO2.visibility = View.VISIBLE

        val switchPM25 = view.findViewById<Switch>(R.id.switchPM25)
        switchPM25.setOnCheckedChangeListener(this)
        if (setPM25.values.isNotEmpty()) switchPM25.visibility = View.VISIBLE

        val switchPM10 = view.findViewById<Switch>(R.id.switchPM10)
        switchPM10.setOnCheckedChangeListener(this)
        if (setPM10.values.isNotEmpty()) switchPM10.visibility = View.VISIBLE

        val switchNO = view.findViewById<Switch>(R.id.switchNO)
        switchNO.setOnCheckedChangeListener(this)
        if (setNO.values.isNotEmpty()) switchNO.visibility = View.VISIBLE

        val switchNH3 = view.findViewById<Switch>(R.id.switchNH3)
        switchNH3.setOnCheckedChangeListener(this)
        if (setNH3.values.isNotEmpty()) switchNH3.visibility = View.VISIBLE

        val switchCO2 = view.findViewById<Switch>(R.id.switchCO2)
        switchCO2.setOnCheckedChangeListener(this)
        if (setCO2.values.isNotEmpty()) switchCO2.visibility = View.VISIBLE

    }

    private fun crearGrafico(result: List<DatosCalidadAire?>) {
        chart.description.text = "Eje Y -> μg/m3, Eje X -> fecha"
        chart.description.setPosition(1000f, 100f)
        chart.description.isEnabled = true


        chart.setTouchEnabled(true)

        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        chart.setPinchZoom(true)

        chart.setDrawGridBackground(false)

        var listaFechas = mutableListOf<String>()
        result.map {
            it?.fecha?.let { it1 -> listaFechas.add(it1.substring(IntRange(0, 9))) }
        }
        val xFormater = IndexAxisValueFormatter(listaFechas.toTypedArray())

        val x = chart.xAxis
        x.valueFormatter = xFormater
        x.position = XAxis.XAxisPosition.BOTTOM
        x.granularity = 1f
        x.mAxisMinimum = -1f
        x.setDrawLabels(true)
        x.setDrawAxisLine(true)
        x.setDrawGridLines(false)
        x.labelRotationAngle = -30f
        x.isEnabled = true


        val y = chart.axisLeft
        y.setLabelCount(9, false)
        y.textColor = Color.BLACK
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        y.setDrawGridLines(false)
        y.setDrawAxisLine(true)
        y.setDrawZeroLine(true)
        y.mAxisMinimum = -1f
        chart.axisLeft.isEnabled = true


        chart.axisRight.isEnabled = false

        // add data
        setData(result)

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.CIRCLE
        l.isEnabled = true

        chart.animateXY(2000, 2000)

        loadingView.visibility = View.GONE
        chart.visibility = View.VISIBLE
        contenedor.visibility = View.VISIBLE
        // don't forget to refresh the drawing
        chart.invalidate()
    }


    private fun setData(result: List<DatosCalidadAire?>) {

        val valuesCO = ArrayList<Entry>()
        val valuesNO2 = ArrayList<Entry>()
        val valuesO3 = ArrayList<Entry>()
        val valuesSO2 = ArrayList<Entry>()
        val valuesPM25 = ArrayList<Entry>()
        val valuesPM10 = ArrayList<Entry>()
        val valuesNO = ArrayList<Entry>()
        val valuesNH3 = ArrayList<Entry>()
        val valuesCO2 = ArrayList<Entry>()

        var date = 0f
        result.map { data ->
            data?.contaminantes?.get("co")?.let {
                valuesCO.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("no2")?.let {
                valuesNO2.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("o3")?.let {
                valuesO3.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("so2")?.let {
                valuesSO2.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("pm25")?.let {
                valuesPM25.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("pm10")?.let {
                valuesPM10.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("no")?.let {
                valuesNO.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("nh3")?.let {
                valuesNH3.add(Entry(date, it?.toFloat()))
            }
            data?.contaminantes?.get("co2")?.let {
                valuesCO2.add(Entry(date, it?.toFloat()))
            }
            date++
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
            when (buttonView.id) {
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
}