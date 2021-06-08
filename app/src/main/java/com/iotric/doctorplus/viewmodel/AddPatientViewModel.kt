package com.iotric.doctorplus.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.RegisterPatientRequest
import com.iotric.doctorplus.model.response.RegisterPatientResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor() : ViewModel() {

    val registerPatientItem = MutableLiveData<RegisterPatientResponse>()

    fun getApiResponse(registerPatientRequest: RegisterPatientRequest, application: Application) {
        ServiceBuilder.getRetrofit(application).registerPatient(registerPatientRequest)
            .enqueue(object : Callback<RegisterPatientResponse> {
                override fun onResponse(
                    call: Call<RegisterPatientResponse>,
                    response: Response<RegisterPatientResponse>
                ) {
                    response.body()?.let {
                        registerPatientItem.postValue(it)
                    }

                }

                override fun onFailure(call: Call<RegisterPatientResponse>, t: Throwable) {

                }


            })
    }

}

