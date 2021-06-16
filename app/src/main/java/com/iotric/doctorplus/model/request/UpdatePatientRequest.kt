package com.iotric.doctorplus.model.request

import com.google.gson.annotations.SerializedName

data class UpdatePatientRequest(

	val nextvisit: String? = null,

	val pname: String? = null
)
