package com.iotric.doctorplus.model.request

import com.google.gson.annotations.SerializedName

data class UpdatePatientRequest(

	@field:SerializedName("patientname")
	val patientname: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
