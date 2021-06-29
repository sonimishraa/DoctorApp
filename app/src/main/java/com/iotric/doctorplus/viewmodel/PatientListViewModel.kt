package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.DeletePatientResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.MyPAtientListResponse
import com.iotric.doctorplus.model.response.PatientStatusChangeResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor() : ViewModel() {

    val allUserList = MutableLiveData<MyPAtientListResponse>()
    val apiErrorMessage = MutableLiveData<String>()
    val deletePatient = MutableLiveData<DeletePatientResponse>()
    val patientStatusChange = MutableLiveData<PatientStatusChangeResponse>()

    fun getApiResponse(application: Application) {
        ServiceBuilder.getRetrofit(application).getMyPatientList().enqueue(object :
            Callback<MyPAtientListResponse> {
            override fun onResponse(
                call: Call<MyPAtientListResponse>,
                response: Response<MyPAtientListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allUserList.postValue(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<MyPAtientListResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    fun getDeleteApiResponse(application: Application, id: String) {
        ServiceBuilder.getRetrofit(application).deletePatient(id).enqueue(object :
            Callback<DeletePatientResponse> {
            override fun onResponse(
                call: Call<DeletePatientResponse>,
                response: Response<DeletePatientResponse>
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        deletePatient.postValue(it)
                        getApiResponse(application)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<DeletePatientResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getStatusChangeApi(application: Application, id: String){
        ServiceBuilder.getRetrofit(application).changePatientStatus(id).enqueue(object : Callback<PatientStatusChangeResponse> {
            override fun onResponse(
                call: Call<PatientStatusChangeResponse>,
                response: Response<PatientStatusChangeResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        patientStatusChange.postValue(it)
                        getApiResponse(application)
                    }
                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<PatientStatusChangeResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}