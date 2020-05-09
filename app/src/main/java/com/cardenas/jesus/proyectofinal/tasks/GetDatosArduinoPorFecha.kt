package com.cardenas.jesus.proyectofinal.tasks

import android.os.Handler
import android.os.Looper
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.cardenas.jesus.proyectofinal.model.mapper.DatosMapper
import com.cardenas.jesus.proyectofinal.view.MainView
import com.cardenas.jesus.proyectofinal.webservices.ApiUtils
import com.cardenas.jesus.proyectofinal.webservices.ResourceServices
import retrofit2.create

class GetDatosArduinoPorFecha(
    params: Params?,
    val view: MainView,
    private val estacion: String,
    val fechaInicial: String,
    val fechaFinal: String
) : Job(params) {
    override fun onRun() {
        val resourceService = ApiUtils.generateRetrofitAPIInstance()
            .create<ResourceServices>()
        val call = resourceService.requesArduinoFecha(estacion, fechaInicial, fechaFinal)
        val result = call.execute().body()

        val datos = DatosMapper().transformDatosArduino(result)

        val uiHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            view.setDataSet(datos)
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