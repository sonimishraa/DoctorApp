package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
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

    fun getApiResponse(doctorRequest: DoctorRegisterRequest, application: Application) {

        ServiceBuilder.getRetrofit(application).registerDoctor(doctorRequest)
            .enqueue(
                object : Callback<DoctorSignUpResponse> {
                    override fun onResponse(
                        call: Call<DoctorSignUpResponse>,
                        response: Response<DoctorSignUpResponse>
                    ) {
                        Log.i("ResisterDoctor", "${response.body()}")
                        response.body()?.let {
                            addDoctorLiveData.postValue(it)
                        }
                    }

                    override fun onFailure(call: Call<DoctorSignUpResponse>, t: Throwable) {
                        Log.e("ResisterDoctor", "${t.message}")
                        addDoctorLiveData.postValue(null)
                    }
                })
    }

}