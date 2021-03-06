package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.AddPatientRequest
import com.iotric.doctorplus.model.response.AddPatientResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor() : ViewModel() {

    val registerPatientItem = MutableLiveData<AddPatientResponse>()
    val registerPatientError = MutableLiveData<String>()

    fun getApi(patient: AddPatientRequest, application: Application){
        //val patientBuilder = requestBody.build().map
        ServiceBuilder.getRetrofit(application).registerPatient(patient).enqueue(object : Callback<AddPatientResponse> {
            override fun onResponse(
                call: Call<AddPatientResponse>,
                response: Response<AddPatientResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        registerPatientItem.postValue(it)
                    }
                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    registerPatientError.postValue(errorResponse?.error?.message ?: "")
                }
            }
            override fun onFailure(call: Call<AddPatientResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
   /* fun getApiResponse( patient: MultipartParams.Builder, application: Application) {
        val patientBuilder = patient.build().map
        ServiceBuilder.getRetrofit(application).addPatient(patientBuilder)
            .enqueue(object : Callback<AddPatientResponse> {
                override fun onResponse(
                    call: Call<AddPatientResponse>,
                    response: Response<AddPatientResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            registerPatientItem.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        registerPatientError.postValue(errorResponse?.error?.message ?: "")
                    }
                }

                override fun onFailure(call: Call<AddPatientResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }*/
}

