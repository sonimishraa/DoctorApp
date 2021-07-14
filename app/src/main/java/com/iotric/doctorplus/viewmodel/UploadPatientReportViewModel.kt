package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.AddPatientReportResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.MultipartParams
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UploadPatientReportViewModel @Inject constructor() : ViewModel() {
   val uploadReport = MutableLiveData<AddPatientReportResponse>()
    val apiErrorMessage = MutableLiveData<String>()

    fun getUploadReportApi(
        report: MultipartParams.Builder,
        application: Application
    ){
        val reportBuilder = report.build().map
        ServiceBuilder.getRetrofit(application).addPatientReport(reportBuilder).enqueue(object : Callback<AddPatientReportResponse> {
            override fun onResponse(
                call: Call<AddPatientReportResponse>,
                response: Response<AddPatientReportResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let{
                        uploadReport.postValue(it)
                    }
                }else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }

            }

            override fun onFailure(call: Call<AddPatientReportResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }
}