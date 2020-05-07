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

class GetWAQIEstaciones(
    params: Params?,
    val view: MainView,
    private val ciudad : String
) : Job(params) {
    override fun onRun() {
        val resourceService = ApiUtils.generateRetrofitWAQIInstance()
            .create<WAQIServices>()

        val call = resourceService.requestDataFromCitySearched(ciudad)
        val result = call.execute().body()

        val uiHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            view.setDataSet(result!!)
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