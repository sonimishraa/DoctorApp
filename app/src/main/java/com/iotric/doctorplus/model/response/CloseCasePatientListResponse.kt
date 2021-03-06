package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class CloseCasePatientListResponse(

	@field:SerializedName("patient")
	val patient: List<CloseCasePatientItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class CloseCasePatientItem(

	@field:SerializedName("address")
	val address: List<String?>? = null,

	@field:SerializedName("pphone")
	val pphone: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("dayofvisit")
	val dayofvisit: String? = null,

	@field:SerializedName("pname")
	val pname: String? = null,

	@field:SerializedName("iscaseopen")
	val iscaseopen: Boolean? = null,

	@field:SerializedName("pemail")
	val pemail: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("prescription")
	val prescription: List<Any?>? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("uniqueid")
	val uniqueid: String? = null,

	@field:SerializedName("age")
	val age: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
