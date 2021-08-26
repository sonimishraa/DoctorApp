package com.iotric.doctorplus.model.request

data class OtpVerificationRequest(
    val phone: String,
    val code: String
)

