package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.DeleteReportResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.GetPrescriptionBypatientIdResponse
import com.iotric.doctorplus.model.response.PatientReportByPatientIdResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPrescripViewModel : ViewModel() {
    val patientPrescrip = MutableLiveData<GetPrescriptionBypatientIdResponse>()
    val getErrorMessage = MutableLiveData<String>()
    val deleteReport = MutableLiveData<DeleteReportResponse>()

    fun getPatientReportApi(patientId: String?, application: Application) {
        ServiceBuilder.getRetrofit(application).getPrescription(patientId)
            .enqueue(object : Callback<GetPrescriptionBypatientIdResponse> {
                override fun onResponse(
                    call: Call<GetPrescriptionBypatientIdResponse>,
                    response: Response<GetPrescriptionBypatientIdResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            patientPrescrip.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        getErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }

                }

                override fun onFailure(call: Call<GetPrescriptionBypatientIdResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    fun deleteReportApi(patientId: String?, reportId: String, application: Application) {
        ServiceBuilder.getRetrofit(application).deleteReport(reportId)
            .enqueue(object : Callback<DeleteReportResponse> {
                override fun onResponse(
                    call: Call<DeleteReportResponse>,
                    response: Response<DeleteReportResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            deleteReport.postValue(it)
                            getPatientReportApi(patientId, application)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        getErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }

                }

                override fun onFailure(call: Call<DeleteReportResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

}