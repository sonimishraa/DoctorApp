package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DeletePatientResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("deletepatient")
	val deletepatient: Any? = null
)
