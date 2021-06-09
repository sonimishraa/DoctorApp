package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditDoctorProfileViewModel @Inject constructor() : ViewModel() {
    var hr = 0
    var min = 0

    fun time1(hr: Int, min: Int): String {
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
    }
}