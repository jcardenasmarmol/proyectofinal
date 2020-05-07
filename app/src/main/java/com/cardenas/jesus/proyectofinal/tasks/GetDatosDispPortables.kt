package com.cardenas.jesus.proyectofinal.tasks

import android.os.Handler
import android.os.Looper
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.cardenas.jesus.proyectofinal.model.mapper.DatosMapper
import com.cardenas.jesus.proyectofinal.view.MyMapView
import com.cardenas.jesus.proyectofinal.webservices.ApiUtils
import com.cardenas.jesus.proyectofinal.webservices.ResourceServices
import com.google.android.gms.maps.GoogleMap
import retrofit2.create

class GetDatosDispPortables(
    params: Params?,
    val view: MyMapView,
    val googleMap: GoogleMap
) : Job(params) {
    override fun onRun() {
        val resourceService = ApiUtils.generateRetrofitAPIInstance()
            .create<ResourceServices>()
        val call = resourceService.requestPortableDeviceData()
        val result = call.execute().body()

        val datos = DatosMapper().transformDatosDispositivoPortable(result)

        val uiHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            view.setDataSet(datos, googleMap)
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