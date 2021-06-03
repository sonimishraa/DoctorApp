package com.iotric.doctorplus.model.response

data class DoctorListsResponse(
    val doctors: List<DoctorsList>
)

data class DoctorsList(
    val address: Array<String>?,
    val clinichours: Array<String>?,
    val _id: String?,
    val doctorname: String?,
    val email: String,
    val phone: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

