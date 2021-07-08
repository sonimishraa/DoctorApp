package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DrSignUpResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("newdoctor")
	val newdoctor: Newdoctor? = null
)

data class Newdoctor(

	@field:SerializedName("clinicendtime")
	val clinicendtime: Any? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("profilepic")
	val profilepic: String? = null,

	@field:SerializedName("adddress")
	val adddress: List<Any?>? = null,

	@field:SerializedName("doctorname")
	val doctorname: String? = null,

	@field:SerializedName("clinicstarttime")
	val clinicstarttime: Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
