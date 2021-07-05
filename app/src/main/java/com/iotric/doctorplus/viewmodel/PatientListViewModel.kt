package com.iotric.doctorplus.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iotric.doctorplus.model.response.*
import com.iotric.doctorplus.networks.ServiceBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor() : ViewModel() {

    val allUserList = MutableLiveData<MyPAtientListResponse>()
    val apiErrorMessage = MutableLiveData<String>()
    val deletePatient = MutableLiveData<DeletePatientResponse>()
    val patientStatusChange = MutableLiveData<PatientStatusChangeResponse>()
    val closePatientList = MutableLiveData<CloseCasePatientListResponse>()
    val changeStatus = MutableLiveData<PatientStatusChangeResponse>()

    fun getActivePatientApiResponse(application: Application) {
        ServiceBuilder.getRetrofit(application).getMyPatientList().enqueue(object :
            Callback<MyPAtientListResponse> {
            override fun onResponse(
                call: Call<MyPAtientListResponse>,
                response: Response<MyPAtientListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allUserList.postValue(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<MyPAtientListResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

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
                        getActivePatientApiResponse(application)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.i("Error", "$errorMessage")
                    val errorResponse =
                        Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                }
            }

            override fun onFailure(call: Call<DeletePatientResponse>, t: Throwable) {
                Toast.makeText(
                    application.applicationContext,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getStatusChangeApi(application: Application, id: String) {
        ServiceBuilder.getRetrofit(application).changePatientStatus(id)
            .enqueue(object : Callback<PatientStatusChangeResponse> {
                override fun onResponse(
                    call: Call<PatientStatusChangeResponse>,
                    response: Response<PatientStatusChangeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            patientStatusChange.postValue(it)
                            getActivePatientApiResponse(application)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }
                }

                override fun onFailure(call: Call<PatientStatusChangeResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    fun getClosePatientApi(application: Application) {
        ServiceBuilder.getRetrofit(application).closecasePatient()
            .enqueue(object : Callback<CloseCasePatientListResponse> {
                override fun onResponse(
                    call: Call<CloseCasePatientListResponse>,
                    response: Response<CloseCasePatientListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            closePatientList.postValue(it)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }
                }

                override fun onFailure(call: Call<CloseCasePatientListResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun closeStatusApi(application: Application, id: String) {
        ServiceBuilder.getRetrofit(application).changePatientStatus(id)
            .enqueue(object : Callback<PatientStatusChangeResponse> {
                override fun onResponse(
                    call: Call<PatientStatusChangeResponse>,
                    response: Response<PatientStatusChangeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            changeStatus.postValue(it)
                            getClosePatientApi(application)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.i("Error", "$errorMessage")
                        val errorResponse =
                            Gson().fromJson(errorMessage, ErrorResponse::class.java)
                        apiErrorMessage.postValue(errorResponse?.error?.message ?: "")
                    }
                }

                override fun onFailure(call: Call<PatientStatusChangeResponse>, t: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }
}