package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class AddPrescripResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
