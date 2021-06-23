package com.iotric.doctorplus.model.response

data class WeeklyAppointmentListResponse(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class DataItem(
	val createdAt: String? = null,
	val address: List<String?>? = null,
	val pphone: String? = null,
	val prescription: List<String?>? = null,
	val doctorid: String? = null,
	val dayofvisit: String? = null,
	val pname: String? = null,
	val V: Int? = null,
	val iscaseopen: Boolean? = null,
	val id: String? = null,
	val visit: List<VisitItems?>? = null,
	val updatedAt: String? = null
)

data class VisitItems(
	val nextvisittime: String? = null,
	val nextvisitdate: String? = null,
	val id: String? = null,
	val isvisted: Boolean? = null
)

