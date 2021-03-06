package com.iotric.doctorplus.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PatientsListsResponse(

	@field:SerializedName("patients")
	val patients: List<PatientsItems?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
@Parcelize
data class PatientsItems(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address")
	val address: List<String?>? = null,

	@field:SerializedName("pphone")
	val pphone: String? = null,

	@field:SerializedName("prescription")
	val prescription: List<String?>? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("dayofvisit")
	val dayofvisit: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("iscaseopen")
	val iscaseopen: Boolean? = null,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("visit")
	val visit: List<VisitsItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("pname")
	val pname: String? = null,

	@field:SerializedName("pemail")
	val pemail: String? = null
):Parcelable

@Parcelize
data class VisitsItem(

	@field:SerializedName("nextvisittime")
	val nextvisittime: String? = null,

	@field:SerializedName("nextvisitdate")
	val nextvisitdate: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("isvisted")
	val isvisted: Boolean? = null
):Parcelable
