package com.iotric.doctorplus.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.networks.ApiService
import com.iotric.doctorplus.room.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao, private val apiService:ApiService) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    suspend fun update(user: User) {
        userDao.updateUser(user)
    }
    suspend fun delete(user: User){
        userDao.deleteUser(user)
    }

    suspend fun getResponse() = apiService.getResponse()

}