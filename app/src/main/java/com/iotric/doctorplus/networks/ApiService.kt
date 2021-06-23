package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.request.UpdateDoctorRequest
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.model.response.*
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.All_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.DOCTOR_LOGIN
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR_ID
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR_NUMBER
import com.iotric.doctorplus.utils.Constants.GET_WEEKLY_APPOINTMENT
import com.iotric.doctorplus.utils.Constants.MY_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.REGISTER_DOCTOR
import com.iotric.doctorplus.utils.Constants.UPDATE_DOCTOR_PROFILE
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ApiService {

    // Doctors Apis

    @GET(GET_DOCTOR_NUMBER)
    fun getDoctor(): Call<DoctorSignUpResponse>

    @GET(GET_DOCTOR_ID)
    fun getDoctorId(@Path("id") id: String?): Call<GetDoctorByidResponse>

    @POST(DOCTOR_LOGIN)
    fun doctorLogin(@Body doctorLoginRequest: DoctorLoginRequest): Call<DoctorLoginResponse>


    @POST(REGISTER_DOCTOR)
    fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DoctorSignUpResponse>

    @PUT(UPDATE_DOCTOR_PROFILE)
    fun updateDoctor(
        @Body updateDoctorRequest: UpdateDoctorRequest
    ): Call<UpdateDoctorResponse>


    // Patients Api

    /*@POST(ADD_PATIENT)
    fun addPatient(@Body addPatientRequest: AddPatientRequest): Call<AddPatientResponse>*/

    @Multipart
    @POST(ADD_PATIENT)
    fun addPatient(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Call<AddPatientResponse>

    @PUT(UPDATE_PATIENT)
    fun updatePatient(
        @Path("id") id: String?,
        @Body updatePatientRequest: UpdatePatientRequest
    ): Call<UpdatePatientResponse>

    @DELETE(DELETE_PATIENT)
    fun deletePatient(@Path("id") id: String?): Call<DeletePatientResponse>

    @GET(All_PATIENT_LIST)
    fun getAllPatientList(): Call<AllPatientListResponse>

    @GET(MY_PATIENT_LIST)
    fun getMyPatientList(): Call<MyPAtientListResponse>

    // Patient Report Apis

    @DELETE(DELETE_REPORT)
    fun deletReport()


    // Appointment Apis
    @GET(GET_WEEKLY_APPOINTMENT)
    fun getWeeklyAppoint(): Call<WeeklyAppointmentListResponse>
}