package com.iotric.doctorplus.model.request

import com.google.gson.annotations.SerializedName

data class UpdatePatientRequest(

	val address: List<String?>? = null,

	@field:SerializedName("nextvisit")
	val nextvisit: List<String>? = null,

	@field:SerializedName("pphone")
	val pphone: String? = null,

	@field:SerializedName("prescription")
	val prescription: List<String>? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("pname")
	val pname: String? = null
)
