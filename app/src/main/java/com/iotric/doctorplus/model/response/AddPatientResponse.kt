package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class AddPatientResponse(
	@field:SerializedName("patients")
	val patients: List<PatientsItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
