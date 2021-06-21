package com.iotric.doctorplus.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetDoctorByidResponse(

	@field:SerializedName("doctor")
	val doctor: Doctor? = null
)
@Parcelize
data class Doctor(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("adddress")
	val adddress: List<String?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("clinichours")
	val clinichours: List<String?>? = null,

	@field:SerializedName("doctorname")
	val doctorname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
):Parcelable