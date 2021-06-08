package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDoctorProfileViewModel @Inject constructor() : ViewModel() {

    fun makeDateString(hr: Int, min: Int): String {
        val time = "$hr:$min"
        return time
    }
}