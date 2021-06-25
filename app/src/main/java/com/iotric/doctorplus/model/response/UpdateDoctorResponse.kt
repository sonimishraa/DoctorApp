package com.iotric.doctorplus.model.response

data class UpdateDoctorResponse(

	val status: Boolean,

	val message: String,

	val updatedoctor: Updatedoctor?
)

data class Updatedoctor(

	val role: String?,

	val id: String?,

	val doctorname: String?,

	val email: String?,

	val type: String?

)
