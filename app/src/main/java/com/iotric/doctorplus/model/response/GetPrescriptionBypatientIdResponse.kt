package com.iotric.doctorplus.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetPrescriptionBypatientIdResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("patientid")
	val patientid: Any? = null,

	@field:SerializedName("precription")
	val precription: List<PrecriptionItem?>? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("isvalid")
	val isvalid: Boolean? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
@Parcelize
data class PrecriptionItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image")
	val image: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
):Parcelable
