package com.iotric.doctorplus.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.iotric.doctorplus.model.PuppyResponse
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.repository.UserRepository
import com.iotric.doctorplus.room.UserDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor( private val repository: UserRepository) : ViewModel() {

    private val allUser = MutableLiveData<PuppyResponse>()
    val userResponse: LiveData<PuppyResponse>
        get() = allUser

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        repository.getResponse().let { response ->

            if (response.isSuccessful) {
                allUser.postValue(response.body())
            }else
                Log.i("Error Response","Get Response:${response.body()}")

        }


    }
}

