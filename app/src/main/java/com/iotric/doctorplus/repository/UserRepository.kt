package com.iotric.doctorplus.repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.networks.ApiService
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.room.UserDao
import retrofit2.http.Body
import javax.inject.Inject

class UserRepository @Inject constructor( val apiService:ApiService,val application: Application) {

}