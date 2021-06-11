package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.response.DoctorSignUpResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterDoctorViewModel @Inject constructor() : ViewModel() {

    val addDoctorLiveData = MutableLiveData<DoctorSignUpResponse?>()
    val addDoctorErrorMessage = MutableLiveData<String>()

    fun getApiResponse(doctorRequest: DoctorRegisterRequest, application: Application) {

       ServiceBuilder.getRetrofit(application).registerDoctor(doctorRequest)
            .enqueue(
                object : Callback<DoctorSignUpResponse> {
                    override fun onResponse(
                        call: Call<DoctorSignUpResponse>,
                        response: Response<DoctorSignUpResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("ResisterDoctor", "${response.body()}")
                            response.body()?.let {
                                addDoctorLiveData.postValue(it)
                            }
                        } else {
                            val errorMessage = response.errorBody()?.string() ?: ""
                            Log.i("Error", "$errorMessage")
                            addDoctorErrorMessage.postValue(errorMessage)
                        }
                    }

                    override fun onFailure(call: Call<DoctorSignUpResponse>, t: Throwable) {
                        Log.e("ResisterDoctor", "${t.message}")
                    }
                })
    }

}