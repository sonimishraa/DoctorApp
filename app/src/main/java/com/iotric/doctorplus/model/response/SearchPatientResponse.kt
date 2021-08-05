package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class SearchPatientResponse(

	@field:SerializedName("data")
	val data: List<PatientsItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
