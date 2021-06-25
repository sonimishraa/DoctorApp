package com.iotric.doctorplus.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetDoctorByidResponse(

	val createdAt: String?,

	val role: String?,

	val type: String?,

	val phone: String? = null,

	val _V: Int? = null,

	val adddress: List<String?>? = null,

	val _id: String? = null,

	val clinichours: List<String?>? = null,

	val doctorname: String? = null,

	val email: String? = null,

	val updatedAt: String? = null

):Parcelable
