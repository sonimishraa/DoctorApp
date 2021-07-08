package com.iotric.doctorplus.model.request


data class UpdateDoctorRequest(

	val type: String?,

	val phone: String?,

	val adddress: String?,

	val clinicstarttime:String,

	val clinicendtime: String,

	val doctorname: String?,


)
