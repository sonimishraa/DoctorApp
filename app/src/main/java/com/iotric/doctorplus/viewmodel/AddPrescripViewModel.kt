package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.AddPatientReportResponse
import com.iotric.doctorplus.model.response.ErrorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPrescripViewModel : ViewModel() {
    val uploadPrescrip = MutableLiveData<AddPatientReportResponse>()
    val apiErrorMessage = MutableLiveData<String>()

    fun getUploadPrescripApi(
        partImage: MultipartBody.Part,
        fname: RequestBody,
        id: RequestBody,
        date: RequestBody,
        application: Application
    ) {
        ServiceBuilder.getRetrofit(application).addPrescrip(partImage, fname, id, date)
            ?.enqueue(object : Callback<AddPatientReportResponse> {
                override fun onResponse(
                    call: Call<AddPatientReportResponse>,
                    response: Response<AddPatientReportResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            uploadPrescrip.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }

                }

                override fun onFailure(call: Call<AddPatientReportResponse>, t: Throwable) {
                    Log.i("UploadImage", "Error Message:${t.message}")
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            })
    }

}