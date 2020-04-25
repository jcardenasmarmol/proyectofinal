package com.cardenas.jesus.proyectofinal.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.config.Configuration
import com.cardenas.jesus.proyectofinal.AppConstants
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.tasks.GetWAQI
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.view.adapters.DatosAirQualiyAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), MainView {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
        val estaciones = listOf(AppConstants.SEVILLACODE, AppConstants.SOFIACODE, AppConstants.GREECECODE)
        val serviceJob = GetWAQI(Params(50).requireNetwork(), this, estaciones)
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
