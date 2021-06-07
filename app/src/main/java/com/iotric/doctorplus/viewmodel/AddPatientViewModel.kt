package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.iotric.doctorplus.model.PuppyResponse
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.model.response.DoctorListsResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.repository.UserRepository
import com.iotric.doctorplus.room.UserDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor( ) : ViewModel() {

    val allUserList = MutableLiveData<DoctorListsResponse>()

    fun getApiResponse(application: Application){
        ServiceBuilder.getRetrofit(application).getDoctorList().enqueue(object : Callback<DoctorListsResponse> {
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

