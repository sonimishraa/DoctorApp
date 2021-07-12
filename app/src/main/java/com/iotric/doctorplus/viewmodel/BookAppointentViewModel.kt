package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.request.AddNewAppointmentRequest
import com.iotric.doctorplus.model.response.AddNewAppointmentResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookAppointentViewModel @Inject constructor() : ViewModel() {
    val newAppointment = MutableLiveData<AddNewAppointmentResponse>()
    val getErrorMessage = MutableLiveData<String>()

    fun getNewAppointmentApi(appoitmentRequest: AddNewAppointmentRequest, application: Application){
        ServiceBuilder.getRetrofit(application).addNewAppointment(appoitmentRequest).enqueue(object :
            Callback<AddNewAppointmentResponse> {
            override fun onResponse(
                call: Call<AddNewAppointmentResponse>,
                response: Response<AddNewAppointmentResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        newAppointment.postValue(it)
                    }
                }
                else{
                    val errorMessage = response.errorBody()?.string()
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    getErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<AddNewAppointmentResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }


        })
    }

}