package com.iotric.doctorplus.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.room.UserDao

class UserRepository(private val userDao: UserDao) {

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

}