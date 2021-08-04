package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class DeleteReportResponse(

	@field:SerializedName("deleteuser")
	val deleteuser: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)
