package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.response.AllDoctorsListResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel : ViewModel() {

    val getDoctorById = MutableLiveData<AllDoctorsListResponse>()
    val getDoctorErrorMessage = MutableLiveData<String>()

    fun getDoctorApi(application: Application) {
        ServiceBuilder.getRetrofit(application).getAllDoctor()
            .enqueue(object : Callback<AllDoctorsListResponse> {
                override fun onResponse(
                    call: Call<AllDoctorsListResponse>,
                    response: Response<AllDoctorsListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            getDoctorById.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        //val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        //getDoctorErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }
                }

                override fun onFailure(call: Call<AllDoctorsListResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


}
