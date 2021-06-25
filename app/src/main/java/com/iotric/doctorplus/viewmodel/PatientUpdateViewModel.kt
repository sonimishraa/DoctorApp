package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.UpdatePatientResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientUpdateViewModel @Inject constructor() : ViewModel() {

    val updatePatientProfile = MutableLiveData<UpdatePatientResponse>()
    val updateError = MutableLiveData<String>()

    fun getUpdateApi(id: String, updatePatient: UpdatePatientRequest, application: Application) {
        ServiceBuilder.getRetrofit(application).updatePatient(id, updatePatient).enqueue(object :
            Callback<UpdatePatientResponse> {
            override fun onResponse(
                call: Call<UpdatePatientResponse>,
                response: Response<UpdatePatientResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        updatePatientProfile.postValue(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    updateError.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<UpdatePatientResponse>, t: Throwable) {

            }


        })

    }

}
