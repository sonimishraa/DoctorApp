package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.*
import com.iotric.doctorplus.model.response.*
import com.iotric.doctorplus.utils.Constants.ADD_NEW_APPOINTMENT
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT_REPORT
import com.iotric.doctorplus.utils.Constants.CHANGE_PATIENT_STATUS
import com.iotric.doctorplus.utils.Constants.CLOSE_CASE_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.DOCTOR_LOGIN
import com.iotric.doctorplus.utils.Constants.GET_DAILY_APPOINTMENT
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR_ID
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR_NUMBER
import com.iotric.doctorplus.utils.Constants.GET_PATIENT_REPORT_BY_ID
import com.iotric.doctorplus.utils.Constants.GET_WEEKLY_APPOINTMENT
import com.iotric.doctorplus.utils.Constants.MY_PATIENT_LIST
import com.iotric.doctorplus.utils.Constants.REGISTER_DOCTOR
import com.iotric.doctorplus.utils.Constants.UPDATE_APPOINTMENT
import com.iotric.doctorplus.utils.Constants.UPDATE_DOCTOR_PROFILE
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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
    fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DrSignUpResponse>

    @PUT(UPDATE_DOCTOR_PROFILE)
    fun updateDoctor(
        @Body updateDoctorRequest: UpdateDoctorRequest
    ): Call<UpdateDoctorResponse>


    // Patients Api

   /* @FormUrlEncoded
    @POST(ADD_PATIENT)
    fun addNewPatient(@FieldMap fields: HashMap<String,String>): Call<AddPatientResponse>*/

   /* @POST(ADD_PATIENT)
    fun addNewPatient(@Body requestBody: RequestBody):Call<AddPatientResponse>*/

    @Multipart
    @POST(ADD_PATIENT)
    fun addPatient(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody> ): Call<AddPatientResponse>


    /* @Multipart
    @POST("XXXX")
    fun myPlans(
        @Part(Constants.ACTION_ID) actionId: RequestBody?,
        @Part(Constants.OFFER_CODE) offerCode: RequestBody?
    ): Call<PlanResponse?>?

    var somevalue = "somevalue"
    var body: RequestBody = create(MediaType.parse("text/plain"), somevalue)
*/
    @PUT(UPDATE_PATIENT)
    fun updatePatient(
        @Path("id") id: String,
        @Body updatePatientRequest: UpdatePatientRequest
    ): Call<UpdatePatientResponse>

    @DELETE(DELETE_PATIENT)
    fun deletePatient(@Path("id") id: String): Call<DeletePatientResponse>

    /*@GET(All_PATIENT_LIST)
    fun getAllPatientList(): Call<AllPatientListResponse>*/

    @GET(MY_PATIENT_LIST)
    fun getMyPatientList(): Call<MyPatientListResponse>

    @PUT(CHANGE_PATIENT_STATUS)
    fun changePatientStatus(@Path("id") id: String):Call<PatientStatusChangeResponse>

    @GET(CLOSE_CASE_PATIENT_LIST)
    fun closecasePatient():Call<CloseCasePatientListResponse>

    @GET(GET_PATIENT_REPORT_BY_ID)
    fun getPatientReport(@Path("id") id: String):Call<PatientReportByPatientIdResponse>


    // Patient Report Apis

/*    @Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@Part images: MultipartBody.Part, @Part ("patientid") patientId: RequestBody): Call<AddPatientReportResponse>*/
    @Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@Part images: MultipartBody.Part): Call<AddPatientReportResponse>


    /*@Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Call<AddPatientReportResponse>*/

    @DELETE(DELETE_REPORT)
    fun deletReport()


    // Appointment Apis

    @GET(GET_DAILY_APPOINTMENT)
    fun getDailyAppoint(): Call<DailyAppointmentResponse>

    @GET(GET_WEEKLY_APPOINTMENT)
    fun getWeeklyAppoint(): Call<DailyAppointmentResponse>

    @POST(ADD_NEW_APPOINTMENT)
    fun addNewAppointment(@Body newAppointment: AddNewAppointmentRequest):Call<AddNewAppointmentResponse>

    @PUT(UPDATE_APPOINTMENT)
    fun updateAppointment(@Path("id") id: String, @Body updateAppoitment: UpdateAppointmentRequest):Call<AddNewAppointmentResponse>


}