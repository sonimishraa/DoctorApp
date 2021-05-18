package com.iotric.doctorplus.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.iotric.doctorplus.AppApplication
import com.iotric.doctorplus.ui.fragment.User
import com.iotric.doctorplus.ui.fragment.UserDatabase
import com.iotric.doctorplus.ui.fragment.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPatientViewModel(application: Application) : AndroidViewModel(application) {

     val allUser: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUser = repository.readAllData
    }

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(user)
        }
    }

}

