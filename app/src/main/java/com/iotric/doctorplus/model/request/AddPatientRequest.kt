package com.iotric.doctorplus.model.request

data class AddPatientRequest(

	val images:String? = null,

	val phone: String? = null,

	val patientname: String? = null,

	val email: String? = null,

	val doctorid: String? = null,

	val nextvisit: String? = null,

	val address: String? = null

)
