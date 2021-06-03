package com.iotric.doctorplus.model.response

data class DoctorSignUpResponse( val message: String, val newdoctor: Newdoctor)

data class Newdoctor (val address: Array<String>,
val clinichours: Array<String>,
val _id: String,
val doctorname: String,
val email: String,
val phone: String,
val createdAt: String,
val updatedAt:String,
  val __v: Int
)



