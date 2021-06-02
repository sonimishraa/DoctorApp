package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : ViewModel() {

    /*  private var loginData: MutableLiveData<DoctorResponse?>

    init {
        loginData = MutableLiveData()
    }

    fun getLoginData(): MutableLiveData<DoctorResponse?>{
        return loginData
    }

   *//* fun fetchLoginRequest(doctorLogin: DoctorLoginRequest){
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