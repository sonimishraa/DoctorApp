package com.iotric.doctorplus.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class GetDoctorByidResponse(

	val profilepic:String,

	val createdAt: String?,

	val role: String?,

	val type: String?,

	val phone: String?,

	val _V: Int?,

	val adddress: List<String?>?,

	val _id: String?,

	val clinicstarttime:String,

	val clinicendtime: String,

	val doctorname: String?,

	val email: String?,

	val education: String,

	val experience:String,

	val updatedAt: String?,

	val gender:String?,

	val hospital:String?,

	val title: String

):Parcelable
