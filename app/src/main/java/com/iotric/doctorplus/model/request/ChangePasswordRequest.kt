package com.iotric.doctorplus.model.request

data class ChangePasswordRequest(
    val phone: String,
    val newpassword: String
)
