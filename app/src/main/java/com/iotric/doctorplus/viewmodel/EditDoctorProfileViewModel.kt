package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.UpdateDoctorRequest
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.UpdateDoctorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EditDoctorProfileViewModel @Inject constructor() : ViewModel() {

    val updateDoctorProfile = MutableLiveData<UpdateDoctorResponse>()
    val updateErrorMessage = MutableLiveData<String>()

    fun getUpdateApi(updateDoctorRequest: UpdateDoctorRequest, application: Application) {
        ServiceBuilder.getRetrofit(application).updateDoctor(updateDoctorRequest).enqueue(object :
            Callback<UpdateDoctorResponse> {
            override fun onResponse(
                call: Call<UpdateDoctorResponse>,
                response: Response<UpdateDoctorResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateDoctorProfile.postValue(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    updateErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }

            }

            override fun onFailure(call: Call<UpdateDoctorResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

}