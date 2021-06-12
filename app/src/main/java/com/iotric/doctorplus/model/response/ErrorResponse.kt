package com.iotric.doctorplus.model.response

data class ErrorResponse(val error: Error?)

data class Error(val message: String?)
