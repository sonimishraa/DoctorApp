package com.iotric.doctorplus.model.request

data class DoctorLoginRequest(
    val phone: String,
    val password: String
)
