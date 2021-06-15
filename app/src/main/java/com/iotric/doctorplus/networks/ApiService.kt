package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.AddPatientRequest
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.model.response.*
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.All_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.DOCTOR_LOGIN
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR
import com.iotric.doctorplus.utils.Constants.REGISTER_DOCTOR
import com.iotric.doctorplus.utils.Constants.REGISTER_PATIENT
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import com.rakuten.common.core.intercept.MockJson
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(GET_DOCTOR)
    //@MockJson("getDoctor.json")
    fun getDoctor(): Call<DoctorSignUpResponse>

    @GET(All_PATIENT_LIST)
    //@MockJson("patientList.json")
    fun getAllPatientList(): Call<AllPatientListResponse>

    @POST(DOCTOR_LOGIN)
    //@MockJson("signUpResponse.json")
    fun doctorLogin(@Body doctorLoginRequest: DoctorLoginRequest): Call<DoctorLoginResponse>

    @POST(REGISTER_DOCTOR)
    //@MockJson("signUpResponse.json")
    fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DoctorSignUpResponse>

    @POST(ADD_PATIENT)

    fun addPatient()

    @POST(DELETE_PATIENT)
    fun deletePatient()

    @POST(REGISTER_PATIENT)
    //@MockJson("registerUserResponse.json")
    fun registerPatient(@Body addPatientRequest: AddPatientRequest): Call<AddPatientResponse>

    @PUT(UPDATE_PATIENT)

    // @MockJson("updatePatientResponse.json")
    fun updatePatient(@Body updatePatientRequest: UpdatePatientRequest): Call<UpdatePatientResponse>

    @DELETE(DELETE_REPORT)
    fun deletReport()
}