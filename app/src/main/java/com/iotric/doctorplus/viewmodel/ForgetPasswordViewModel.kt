package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.ChangePasswordRequest
import com.iotric.doctorplus.model.request.OtpVerificationRequest
import com.iotric.doctorplus.model.response.ChangePasswordResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.model.response.OtpVerificationResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : ViewModel() {

    val verifyOtp = MutableLiveData<OtpVerificationResponse>()
    val apiErrorMessage = MutableLiveData<String>()

    val changePassword = MutableLiveData<ChangePasswordResponse>()


    fun verifyOtpApi(otpVerificationReq: OtpVerificationRequest, application: Application){
        ServiceBuilder.getRetrofit(application).verifyOtp(otpVerificationReq).enqueue(object :
            Callback<OtpVerificationResponse> {
            override fun onResponse(
                call: Call<OtpVerificationResponse>,
                response: Response<OtpVerificationResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        verifyOtp.postValue(it)
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
            override fun onFailure(call: Call<OtpVerificationResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun changePasswordApi(changePasswordReq: ChangePasswordRequest, application: Application){
        ServiceBuilder.getRetrofit(application).changePassword(changePasswordReq).enqueue(object :
            Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        changePassword.postValue(it)
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
            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }





}
