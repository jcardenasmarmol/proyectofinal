package com.cardenas.jesus.proyectofinal.tasks

import android.os.Handler
import android.os.Looper
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.cardenas.jesus.proyectofinal.model.DatosAirQualityModel
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.webservices.ApiUtils
import com.cardenas.jesus.proyectofinal.webservices.WAQIServices
import retrofit2.create

class GetWAQI (
    params: Params?,
    val view: MainView,
    private val estaciones : List<String>
) : Job(params) {
    override fun onRun() {
        val resourceService = ApiUtils.generateRetrofitWAQIInstance()
            .create<WAQIServices>()


        var list = mutableListOf<DatosAirQualityModel?>()
        estaciones.map {
            val call = resourceService.requestLastDataFromStation(it)
            val result = call.execute().body()
            list.add(result)
        }

        val uiHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            view.setDataSet(list)
        }
        uiHandler.post(runnable)
    }

    override fun shouldReRunOnThrowable(
        throwable: Throwable,
        runCount: Int,
        maxRunCount: Int
    ): RetryConstraint {
        return RetryConstraint(false)
    }

    override fun onAdded() {
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
    }
}