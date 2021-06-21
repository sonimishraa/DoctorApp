package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class UpdateDoctorResponse(

	@field:SerializedName("updatedoctor")
	val updatedoctor: Updatedoctor? = null
)

data class Updatedoctor(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("doctorname")
	val doctorname: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
