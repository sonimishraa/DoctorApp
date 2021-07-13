package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class AddNewAppointmentResponse(

	@field:SerializedName("resp")
	val resp: Resp? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Resps(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nextvisittime")
	val nextvisittime: String? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("patientid")
	val patientid: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("nextvisitdate")
	val nextvisitdate: String? = null,

	@field:SerializedName("isvisited")
	val isvisited: Boolean? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
