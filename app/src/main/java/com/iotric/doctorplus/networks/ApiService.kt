package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.request.RegisterPatientRequest
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.model.response.DoctorSignUpResponse
import com.iotric.doctorplus.model.response.PatientsListResponse
import com.iotric.doctorplus.model.response.RegisterPatientResponse
import com.iotric.doctorplus.model.response.UpdatePatientResponse
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR
import com.iotric.doctorplus.utils.Constants.GET_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.REGISTER_DOCTOR
import com.iotric.doctorplus.utils.Constants.REGISTER_PATIENT
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import com.rakuten.common.core.intercept.MockJson
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(GET_DOCTOR)
    @MockJson("getDoctor.json")
    fun getDoctor(): Call<DoctorSignUpResponse>

    @GET(GET_PATIENT_LIST)
    @MockJson("patientList.json")
    fun getDoctorList():Call<PatientsListResponse>

    @POST(REGISTER_DOCTOR)
    @Headers("Content-Type:application/json")
    @MockJson("signUpResponse.json")
    fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DoctorSignUpResponse>

    @POST(ADD_PATIENT)
    fun addPatient()

    @POST(DELETE_PATIENT)
    fun deletePatient()

    @POST(REGISTER_PATIENT)
    @Headers("Content-Type:application/json")
    @MockJson("registerUserResponse.json")
    fun registerPatient(@Body registerPatientRequest:RegisterPatientRequest):Call<RegisterPatientResponse>

    @PUT(UPDATE_PATIENT)
    @Headers("Content-Type:application/json")
    @MockJson("updatePatientResponse.json")
    fun updatePatient(@Body updatePatientRequest: UpdatePatientRequest):Call<UpdatePatientResponse>

    @DELETE(DELETE_REPORT)
    fun deletReport()
}