package com.iotric.doctorplus.model.request


data class DoctorList(val data: List<Doctor?>)

data class DoctorResponse(val code:Int?, val meta: String?, val data: Doctor)

data class Doctor(val id: String, val name: String,
                 val email: String,
                 val  phone: String,
                 val password: String,
                 val address: String)


