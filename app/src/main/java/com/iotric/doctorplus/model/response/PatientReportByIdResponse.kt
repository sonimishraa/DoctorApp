package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class PatientReportByIdResponse(

	@field:SerializedName("report")
	val report: List<ReportItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ReportItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doctorid")
	val doctorid: String? = null,

	@field:SerializedName("patientid")
	val patientid: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("labreports")
	val labreports: List<LabreportsItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class LabreportsItem(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("dateofreport")
	val dateofreport: String? = null,

	@field:SerializedName("reportname")
	val reportname: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
