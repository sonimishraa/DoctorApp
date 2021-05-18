package com.iotric.doctorplus.ui.fragment

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

}