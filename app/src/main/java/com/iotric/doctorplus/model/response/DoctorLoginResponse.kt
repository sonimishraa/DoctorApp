package com.iotric.doctorplus.model.response

data class DoctorLoginResponse(
    val message:String,
    val _id: String,
    val authToken: String,
    val status: Boolean

)