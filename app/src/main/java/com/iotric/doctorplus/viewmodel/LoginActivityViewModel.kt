package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.response.DoctorLoginResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : ViewModel() {

    val loginData = MutableLiveData<DoctorLoginResponse?>()
    val loginError = MutableLiveData<String>()

    fun fetchLoginRequest(doctorLoginRequest: DoctorLoginRequest, application: Application) {
        ServiceBuilder.getRetrofit(application).doctorLogin(doctorLoginRequest).enqueue(object :
            retrofit2.Callback<DoctorLoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<DoctorLoginResponse>,
                response: retrofit2.Response<DoctorLoginResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        loginData.postValue(it)
                    }
                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    loginError.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: retrofit2.Call<DoctorLoginResponse>, t: Throwable) {
            }

        })
    }
}