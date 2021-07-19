package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.ForgetPasswordOtpRequest
import com.iotric.doctorplus.model.response.DoctorLoginResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.ForgetPasswordOtpResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : ViewModel() {

    val loginData = MutableLiveData<DoctorLoginResponse?>()
    val loginError = MutableLiveData<String>()

    val forgetPasswordOtp = MutableLiveData<ForgetPasswordOtpResponse>()
    val apiErrorMessage = MutableLiveData<String>()


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
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getOtpApi(otpRequest: ForgetPasswordOtpRequest, application: Application){
        ServiceBuilder.getRetrofit(application).forgetPasswordOtp(otpRequest).enqueue(object :
            Callback<ForgetPasswordOtpResponse> {
            override fun onResponse(
                call: Call<ForgetPasswordOtpResponse>,
                response: Response<ForgetPasswordOtpResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        forgetPasswordOtp.postValue(it)
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
            override fun onFailure(call: Call<ForgetPasswordOtpResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}