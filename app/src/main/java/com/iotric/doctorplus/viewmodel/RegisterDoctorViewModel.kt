package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.response.DoctorSignUpResponse
import com.iotric.doctorplus.model.response.DrSignUpResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterDoctorViewModel @Inject constructor() : ViewModel() {

    val addDoctorLiveData = MutableLiveData<DrSignUpResponse>()
    val addDoctorErrorMessage = MutableLiveData<String>()

    fun getApiResponse(doctorRequest: DoctorRegisterRequest, application: Application) {

        ServiceBuilder.getRetrofit(application).registerDoctor(doctorRequest)
            .enqueue(
                object : Callback<DrSignUpResponse> {
                    override fun onResponse(
                        call: Call<DrSignUpResponse>,
                        response: Response<DrSignUpResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("ResisterDoctor", "${response.body()}")
                            response.body()?.let {
                                addDoctorLiveData.postValue(it)
                            }
                        } else {
                            val errorMessage = response.errorBody()?.string()
                            Log.i("Error", "$errorMessage")
                            val errorResponse =
                                Gson().fromJson(errorMessage, ErrorResponse::class.java)
                            addDoctorErrorMessage.postValue(errorResponse?.error?.message ?: "")
                        }
                    }

                    override fun onFailure(call: Call<DrSignUpResponse>, t: Throwable) {
                        Log.e("ResisterDoctor", "${t.message}")
                    }
                })
    }

}