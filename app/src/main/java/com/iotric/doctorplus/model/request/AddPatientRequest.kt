package com.iotric.doctorplus.model.request

data class AddPatientRequest(
	val phone : String,
	val patientname : String,
	val email: String,
	val address: String?,
	val age: String,
	val gender:String,
	val bloodgroup: String,
	val symptoms: String

)
