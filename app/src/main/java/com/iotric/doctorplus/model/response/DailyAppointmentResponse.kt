package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DailyAppointmentResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Patientid(

	@field:SerializedName("address")
	val address: List<String?>? = null,

	@field:SerializedName("pphone")
	val pphone: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("age")
	val age: String? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nextvisittime")
	val nextvisittime: String? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("patientid")
	val patientid: Patientid? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("nextvisitdate")
	val nextvisitdate: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("isvisited")
	val isvisited: Boolean? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
