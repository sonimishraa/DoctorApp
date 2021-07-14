package com.iotric.doctorplus.model.request

data class UpdateAppointmentRequest(
    val nextvisitdate: String?,
    val nextvisittime: String?,
    val description: String?

)


