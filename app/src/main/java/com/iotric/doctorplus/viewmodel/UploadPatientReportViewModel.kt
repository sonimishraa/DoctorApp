package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.AddPatientReportResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class UploadPatientReportViewModel @Inject constructor() : ViewModel() {
    val uploadReport = MutableLiveData<AddPatientReportResponse>()
    val apiErrorMessage = MutableLiveData<String>()

    fun getUploadReportApi(
        partImage: MultipartBody.Part,
        fname: RequestBody,
        id: RequestBody,
        date: RequestBody,
        application: Application
    ) {
        ServiceBuilder.getRetrofit(application).addReport(partImage, fname, id, date)
            ?.enqueue(object : Callback<AddPatientReportResponse> {
                override fun onResponse(
                    call: Call<AddPatientReportResponse>,
                    response: Response<AddPatientReportResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            uploadReport.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }

                }

                override fun onFailure(call: Call<AddPatientReportResponse>, t: Throwable) {
                    Log.i("UploadImage", "Error Message:${t.message}")
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            })
    }

    fun getreportUloadApi(
        patient: HashMap<String, RequestBody>,
        application: Application
    ) {
        //val patientBuilder = patient.build().map
        ServiceBuilder.getRetrofit(application).addPatientReprt(patient)
            .enqueue(object : Callback<AddPatientReportResponse> {
                override fun onResponse(
                    call: Call<AddPatientReportResponse>,
                    response: Response<AddPatientReportResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            uploadReport.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }

                }

                override fun onFailure(call: Call<AddPatientReportResponse>, t: Throwable) {
                    Log.i("UploadImage", "Error Message:${t.message}")
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            })
    }
}