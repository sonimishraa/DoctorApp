package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.PatientStatusChangeResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InActivePatientViewModel @Inject constructor() : ViewModel() {

    val closePatientList = MutableLiveData<PatientStatusChangeResponse>()
    val getErrorMessage = MutableLiveData<String>()



    fun getClosePatientApi(patientId: String, application: Application) {
        ServiceBuilder.getRetrofit(application).changePatientStatus(patientId)
            .enqueue(object : Callback<PatientStatusChangeResponse> {
                override fun onResponse(
                    call: Call<PatientStatusChangeResponse>,
                    response: Response<PatientStatusChangeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            closePatientList.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        getErrorMessage.postValue(errorResponse?.error?.message ?: "")
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
