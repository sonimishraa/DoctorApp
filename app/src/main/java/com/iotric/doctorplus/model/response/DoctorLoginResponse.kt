package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DoctorLoginResponse(

	@field:SerializedName("authToken")
	val authToken: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
