package com.cardenas.jesus.proyectofinal.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetUltimosArduino
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.view.adapters.DatosAirQualiyAdapter
import kotlinx.android.synthetic.main.fragment_ultimos_arduino.*

class UltimosArduinoFragment : Fragment(), MainView {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ultimos_arduino, container, false)
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
        val serviceJob = GetUltimosArduino(Params(50).requireNetwork(),this)
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: List<DatosAirQualityModel?>) {
        mainRecyclerView?.layoutManager = LinearLayoutManager(this.context)
        mainRecyclerView?.adapter = DatosAirQualiyAdapter(result)
        loadingView?.visibility = View.GONE
        mainRecyclerView?.visibility = View.VISIBLE
    }
}
