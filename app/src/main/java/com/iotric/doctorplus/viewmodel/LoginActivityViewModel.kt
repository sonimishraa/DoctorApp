package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.DoctorResponse
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(val userDao: UserDao): ViewModel() {

      private var loginData: MutableLiveData<DoctorResponse?>

    init {
        loginData = MutableLiveData()
    }

    fun getLoginData(): MutableLiveData<DoctorResponse?>{
        return loginData
    }

   /* fun fetchLoginRequest(doctorLogin: DoctorLoginRequest){
        ServiceBuilder.apiService.doctorLogin(doctorLogin).enqueue(object :
            retrofit2.Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {
                response.body()?.let {
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                loginData.postValue(null)
            }

        })
    }*/
}