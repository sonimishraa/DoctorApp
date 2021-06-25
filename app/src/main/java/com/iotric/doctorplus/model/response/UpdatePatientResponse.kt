package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class UpdatePatientResponse(

	val updatedpatient: Updatedpatient? = null,

	val message: String? = null,

	val status: Boolean
)
/*
{
	"message": "Patient details added",
	"status": true,
	"updatedpatient": {
	"prescription": [],
	"address": [
	"jadgf"
	],
	"dayofvisit": "2021-06-22T10:13:36.324Z",
	"iscaseopen": true,
	"_id": "60d435d31f8404b748aa2695",
	"doctorid": "60d082b3c0a23e8c4bf20625",
	"pname": "Arya",
	"pemail": "s@gmail.com",
	"pphone": "8360036009",
	"visit": [
	{
		"isvisted": false,
		"_id": "60d5a5111f8404b748aa26a3",
		"nextvisittime": "12:00AM"
	}
	],
	"createdAt": "2021-06-24T07:35:47.605Z",
	"updatedAt": "2021-06-25T09:42:41.081Z",
	"__v": 0
}
}*/

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
