package com.iotric.doctorplus.viewmodel

import android.net.DnsResolver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.Doctor
import com.iotric.doctorplus.model.request.DoctorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddDoctorViewModel @Inject constructor() : ViewModel() {

    private var addDoctor: MutableLiveData<DoctorResponse?>

    init {
        addDoctor = MutableLiveData()
    }

    fun addNewDoctor(): MutableLiveData<DoctorResponse?> {
        return addDoctor
    }

    fun getApiRequest(doctor: Doctor) {
        ServiceBuilder.apiService.addDoctor(doctor).enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {

                response.body()?.let {
                    addDoctor.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                addDoctor.postValue(null)
            }

        })

    }

}