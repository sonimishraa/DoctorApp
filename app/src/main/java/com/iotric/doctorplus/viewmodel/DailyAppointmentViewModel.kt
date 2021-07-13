package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.DailyAppointmentResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DailyAppointmentViewModel @Inject constructor(): ViewModel() {

    val getDailyAppointment = MutableLiveData<DailyAppointmentResponse>()
    val getErrorMessage = MutableLiveData<String>()

    fun getAppointApi(application: Application){
        ServiceBuilder.getRetrofit(application).getDailyAppoint().enqueue(object : Callback<DailyAppointmentResponse> {
            override fun onResponse(
                call: Call<DailyAppointmentResponse>,
                response: Response<DailyAppointmentResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        getDailyAppointment.postValue(it)
                    }
                }else{
                    val errorMessage = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    getErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<DailyAppointmentResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }
}