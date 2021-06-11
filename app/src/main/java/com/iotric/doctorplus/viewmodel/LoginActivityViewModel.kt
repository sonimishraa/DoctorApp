package com.iotric.doctorplus.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.response.DoctorLoginResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : ViewModel() {

    val loginData = MutableLiveData<DoctorLoginResponse?>()

    fun fetchLoginRequest(doctorLoginRequest: DoctorLoginRequest, application: Application) {
        ServiceBuilder.getRetrofit(application).doctorLogin(doctorLoginRequest).enqueue(object :
            retrofit2.Callback<DoctorLoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<DoctorLoginResponse>,
                response: retrofit2.Response<DoctorLoginResponse>
            ) {
                response.body()?.let {
                    loginData.postValue(it)
                }
            }

            override fun onFailure(call: retrofit2.Call<DoctorLoginResponse>, t: Throwable) {
                loginData.postValue(null)
            }

        })
    }
}