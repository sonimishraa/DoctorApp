package com.iotric.doctorplus.model.request

import com.google.gson.annotations.SerializedName


/*{
	"pname":"Arya",
	"pphone":"8360036009",
	"nextvisitDate":"20/1/2021",
	"nextvisittime":"12:00AM"
}*/
data class UpdatePatientRequest(
	val pname: String?,
	val pphone: String?,
	val age:String?,
	val healthIssue:String?,
	val address:String,
	val bloodgroup:String

)

