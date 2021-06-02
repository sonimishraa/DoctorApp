package com.iotric.doctorplus.viewmodel

import android.app.Application
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

    val addDoctorLiveData = MutableLiveData<DoctorSignUpResponse>()

    fun getApiResponse(doctorRequest: DoctorRegisterRequest) {
        ServiceBuilder.getRetrofit(application = Application()).registerDoctor(doctorRequest)
            .enqueue(
                object : Callback<DoctorSignUpResponse> {
                    override fun onResponse(
                        call: Call<DoctorSignUpResponse>,
                        response: Response<DoctorSignUpResponse>
                    ) {

                        response.body()?.let {
                            addDoctorLiveData.postValue(response.body())
                        }

                    }

                    override fun onFailure(call: Call<DoctorSignUpResponse>, t: Throwable) {
                        //addDoctorLiveData.postValue()
                    }

                })
    }

}