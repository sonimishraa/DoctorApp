package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.PatientReportByIdResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientRecordViewModel @Inject constructor() : ViewModel() {

    val patientRecord = MutableLiveData<PatientReportByIdResponse>()
    val getErrorMessage = MutableLiveData<String>()

    fun getPatientReportApi(id: String, application: Application){
        ServiceBuilder.getRetrofit(application).getPatientReport(id).enqueue(object : Callback<PatientReportByIdResponse> {
            override fun onResponse(
                call: Call<PatientReportByIdResponse>,
                response: Response<PatientReportByIdResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        patientRecord.postValue(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    getErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }

            }

            override fun onFailure(call: Call<PatientReportByIdResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }

}