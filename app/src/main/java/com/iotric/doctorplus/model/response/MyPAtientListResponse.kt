package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class MyPAtientListResponse(

	@field:SerializedName("patients")
	val patients: List<PatientsItems?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
