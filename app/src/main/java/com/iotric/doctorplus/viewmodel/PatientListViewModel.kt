package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.response.DeletePatientResponse
import com.iotric.doctorplus.model.response.MyPAtientListResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor() : ViewModel() {

    val allUserList = MutableLiveData<MyPAtientListResponse>()

    val deletePatient = MutableLiveData<DeletePatientResponse>()

    fun getApiResponse(application: Application) {
        ServiceBuilder.getRetrofit(application).getMyPatientList().enqueue(object :
            Callback<MyPAtientListResponse> {
            override fun onResponse(
                call: Call<MyPAtientListResponse>,
                response: Response<MyPAtientListResponse>
            ) {
                Log.i(
                    "PatientListViewModel",
                    "status = ${response.isSuccessful} : msg = ${response.body()?.message}"
                )
                response.body()?.let {
                    allUserList.postValue(it)
                }
                Log.e("PatientListViewModel", "error = ${response.errorBody()?.string()}")

            }

            override fun onFailure(call: Call<MyPAtientListResponse>, t: Throwable) {
                Log.e("PatientListViewModel", "error = ${t.message}")

            }
        })
    }

    fun getDeleteApiResponse(application: Application, id: String) {
        ServiceBuilder.getRetrofit(application).deletePatient(id).enqueue(object :
            Callback<DeletePatientResponse> {
            override fun onResponse(
                call: Call<DeletePatientResponse>,
                response: Response<DeletePatientResponse>
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        deletePatient.postValue(it)
                        getApiResponse(application)
                    }
                }
            }

            override fun onFailure(call: Call<DeletePatientResponse>, t: Throwable) {
            }

        })
    }


}