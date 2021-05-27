package com.iotric.doctorplus.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.networks.ApiService
import com.iotric.doctorplus.room.UserDao
import retrofit2.http.Body
import javax.inject.Inject

class UserRepository @Inject constructor( private val apiService:ApiService) {

    @WorkerThread

    suspend fun getDoctorResponse() = apiService.getDoctor()

   // suspend fun addDoctor() = apiService.addDoctor()

    suspend fun addPatient() = apiService.addPatient()

    suspend fun updatePatient() = apiService.updatePatient()

    suspend fun deletePatient() = apiService.deletePatient()

}