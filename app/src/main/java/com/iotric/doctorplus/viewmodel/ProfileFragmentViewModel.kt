package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.response.GetDoctorByidResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel : ViewModel() {

    val getDoctorById = MutableLiveData<GetDoctorByidResponse>()
    val getDoctorErrorMessage = MutableLiveData<String>()

    fun getDoctorApi(id: String?, application: Application) {
        ServiceBuilder.getRetrofit(application).getDoctorId(id)
            .enqueue(object : Callback<GetDoctorByidResponse> {
                override fun onResponse(
                    call: Call<GetDoctorByidResponse>,
                    response: Response<GetDoctorByidResponse>
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

                override fun onFailure(call: Call<GetDoctorByidResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}
