package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.response.DoctorListsResponse
import com.iotric.doctorplus.model.response.DoctorSignUpResponse
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR_LIST
import com.iotric.doctorplus.utils.Constants.REGISTER_DOCTOR
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import com.rakuten.common.core.intercept.MockJson
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(GET_DOCTOR)
    @MockJson("getDoctor.json")
    fun getDoctor(): Call<DoctorSignUpResponse>

    @GET(GET_DOCTOR_LIST)
    @MockJson("getAllDoctor.json")
    fun getDoctorList():Call<DoctorListsResponse>

    @POST(REGISTER_DOCTOR)
    @Headers("Content-Type:application/json")
    @MockJson("signUpRequest.json")
    fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DoctorSignUpResponse>

    @POST(ADD_PATIENT)
    fun addPatient()

    @POST(DELETE_PATIENT)

    fun deletePatient()

    @PUT(UPDATE_PATIENT)
    fun updatePatient()

    @DELETE(DELETE_REPORT)
    fun deletReport()
}