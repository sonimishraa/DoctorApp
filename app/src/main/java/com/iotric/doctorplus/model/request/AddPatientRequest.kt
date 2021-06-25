package com.iotric.doctorplus.model.request


data class AddPatientRequest(

	val images: String? = null,

	val phone : String,

	val patientname : String,

	val nextvisitdate: String? = null,

	val nextvisittime:String? = null,

	val email: String,

	val address: String? = null

)
