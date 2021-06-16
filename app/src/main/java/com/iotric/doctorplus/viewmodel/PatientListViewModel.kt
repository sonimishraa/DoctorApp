package com.iotric.doctorplus.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.response.AllPatientListResponse
import com.iotric.doctorplus.model.response.DeletePatientResponse
import com.iotric.doctorplus.model.response.MyPAtientListResponse
import com.iotric.doctorplus.model.response.PatientsListResponse
import com.iotric.doctorplus.networks.ApiService
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

    fun getApiResponse(application: Application){
        ServiceBuilder.getRetrofit(application).getMyPatientList().enqueue(object :
            Callback< MyPAtientListResponse> {
            override fun onResponse(
                call: Call<MyPAtientListResponse>,
                response: Response< MyPAtientListResponse>
            ) {
                response.body()?.let{
                    allUserList.postValue(it)
                }

            }

            override fun onFailure(call: Call< MyPAtientListResponse>, t: Throwable) {

            }


        })
    }

    fun getDeleteApiResponse(application: Application, id: String){
        ServiceBuilder.getRetrofit(application).deletePatient(id).enqueue(object :
            Callback< DeletePatientResponse> {
            override fun onResponse(
                call: Call<DeletePatientResponse>,
                response: Response< DeletePatientResponse>
            ) {
                if(response.isSuccessful) {
                    response.body().let {
                        deletePatient.postValue(it)
                    }
                }
            }
            override fun onFailure(call: Call< DeletePatientResponse>, t: Throwable) {
            }

        })
    }


}