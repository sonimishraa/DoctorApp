package com.iotric.doctorplus.model.response

import com.google.gson.annotations.SerializedName

data class ForgetPasswordOtpResponse(

	@field:SerializedName("verification")
	val verification: Verification? = null
)

data class Verification(

	@field:SerializedName("lookup")
	val lookup: Lookup? = null,

	@field:SerializedName("amount")
	val amount: Any? = null,

	@field:SerializedName("sendCodeAttempts")
	val sendCodeAttempts: List<SendCodeAttemptsItem?>? = null,

	@field:SerializedName("channel")
	val channel: String? = null,

	@field:SerializedName("serviceSid")
	val serviceSid: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("sid")
	val sid: String? = null,

	@field:SerializedName("dateUpdated")
	val dateUpdated: String? = null,

	@field:SerializedName("valid")
	val valid: Boolean? = null,

	@field:SerializedName("payee")
	val payee: Any? = null,

	@field:SerializedName("dateCreated")
	val dateCreated: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("accountSid")
	val accountSid: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class SendCodeAttemptsItem(

	@field:SerializedName("attempt_sid")
	val attemptSid: String? = null,

	@field:SerializedName("channel")
	val channel: String? = null,

	@field:SerializedName("time")
	val time: String? = null
)

data class Lookup(

	@field:SerializedName("carrier")
	val carrier: Carrier? = null
)


data class Carrier(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("error_code")
	val errorCode: Any? = null,

	@field:SerializedName("mobile_country_code")
	val mobileCountryCode: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("mobile_network_code")
	val mobileNetworkCode: String? = null
)
