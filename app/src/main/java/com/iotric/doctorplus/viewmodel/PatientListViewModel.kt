package com.iotric.doctorplus.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.response.DoctorListsResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor() : ViewModel() {

    val allUserList = MutableLiveData<DoctorListsResponse>()

    fun getApiResponse(application: Application){
        ServiceBuilder.getRetrofit(application).getDoctorList().enqueue(object :
            Callback<DoctorListsResponse> {
            override fun onResponse(
                call: Call<DoctorListsResponse>,
                response: Response<DoctorListsResponse>
            ) {
                response.body()?.let{
                    allUserList.postValue(it)
                }

            }

            override fun onFailure(call: Call<DoctorListsResponse>, t: Throwable) {

            }


        })
    }


}