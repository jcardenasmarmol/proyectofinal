package com.cardenas.jesus.proyectofinal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration

import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetWAQIEstaciones
import com.cardenas.jesus.proyectofinal.view.MainView
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), MainView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchBtn = view.findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener {
            //loadData(view)
            //Toast.makeText(context, "Buscar", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun loadData(view: View?) {
        val builder = this.context?.let {
            Configuration.Builder(it)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(2)
                .consumerKeepAlive(200)
        }

        val jobManager = JobManager(builder?.build())

        val serviceJob = GetWAQIEstaciones(Params(50).requireNetwork(), this, search_box.text.toString())
        jobManager.addJobInBackground(serviceJob)
        jobManager.start()
    }

    override fun setDataSet(result: List<DatosAirQualityModel?>) {
        Toast.makeText(context, "funciona", Toast.LENGTH_SHORT).show()
    }

}
