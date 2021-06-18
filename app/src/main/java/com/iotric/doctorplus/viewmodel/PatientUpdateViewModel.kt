package com.iotric.doctorplus.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.model.response.UpdatePatientResponse
import java.util.*

class PatientUpdateViewModel : ViewModel() {
    var hr = 0
    var min = 0
    val updatePatient = MutableLiveData<UpdatePatientResponse>()

    fun getUpdateApi(updatePatient: UpdatePatientRequest, application: Application) {
        // ServiceBuilder.getRetrofit(application).updatePatient()

    }

   /* fun time1(hr: Int, min: Int): String {
        val time1 = "$hr:$min" + timeFormate(hr)
        return time1
    }

    fun timeFormate(hr: Int): String {
        if (hr <= 12)
            return "AM"
        if (hr > 12)
            return "PM"
        else
            return "AM"
    }

    fun getTimeCalender() {
        val calendar = Calendar.getInstance()
        hr = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
    }*/
}
