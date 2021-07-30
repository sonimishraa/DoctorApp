package com.iotric.doctorplus.model.request


data class UpdateDoctorRequest(

	val title: String?,

	val gender: String?,

	val clinicstarttime:String?,

	val clinicendtime: String?,

	val education: String?,

	val hospital:String?,

	val experience:String?


)
