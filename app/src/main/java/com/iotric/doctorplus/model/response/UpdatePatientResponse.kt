package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class UpdatePatientResponse(

	val updatedpatient: Updatedpatient? = null,

	val message: String? = null,

	val status: Boolean
)
data class Updatedpatient(

	val address: List<String>? = null,

	val pphone: String? = null,

	val dayofvisit: String? = null,

	val pname: String? = null,

	val iscaseopen: Boolean? = null,

	val pemail: String? = null,

	val createdAt: String? = null,

	val prescription: List<String?>? = null,

	val doctorid: String? = null,

	val _v: Int? = null,

	val _id: String? = null,

	val visit: List<VisitItem?>? = null,

	val updatedAt: String? = null
)

data class VisitItem(

	val nextvisittime: String? = null,

	val _id: String? = null,

	val isvisted: Boolean? = null
)
