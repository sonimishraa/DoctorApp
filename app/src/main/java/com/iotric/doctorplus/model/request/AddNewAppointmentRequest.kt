package com.iotric.doctorplus.model.request

data class AddNewAppointmentRequest (
    val patientid:String,
    val nextvisitdate:String,
    val nextvisittime: String,
    val description:String
)


