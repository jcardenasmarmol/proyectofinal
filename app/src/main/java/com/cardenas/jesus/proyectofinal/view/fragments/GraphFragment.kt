package com.cardenas.jesus.proyectofinal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration

import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetHistoricosPorFecha
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.view.adapters.DatosAirQualiyAdapter
import kotlinx.android.synthetic.main.fragment_graph.*

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
            arguments?.getString("fechaInicial")?:"2020-01-01",
            arguments?.getString("fechaFinal")?:"2020-01-15")
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: List<DatosAirQualityModel?>) {
        loadingView?.visibility = View.GONE
    }

}
