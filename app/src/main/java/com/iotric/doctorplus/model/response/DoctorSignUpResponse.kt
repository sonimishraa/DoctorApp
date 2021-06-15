package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DoctorSignUpResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("adddress")
	val adddress: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("clinichours")
	val clinichours: String? = null,

	@field:SerializedName("metainfo")
	val metainfo: String? = null,

	@field:SerializedName("doctorname")
	val doctorname: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
