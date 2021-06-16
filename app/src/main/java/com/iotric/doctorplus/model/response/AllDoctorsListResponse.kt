package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class AllDoctorsListResponse(

	@field:SerializedName("doctors")
	val doctors: List<DoctorsItem>
)

data class DoctorsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("adddress")
	val adddress: List<Any?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("clinichours")
	val clinichours: List<Any?>? = null,

	@field:SerializedName("doctorname")
	val doctorname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
