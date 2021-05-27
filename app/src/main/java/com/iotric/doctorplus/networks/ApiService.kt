package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.request.Doctor
import com.iotric.doctorplus.model.request.DoctorResponse
import com.iotric.doctorplus.utils.Constants.ADD_DOCTOR
import com.iotric.doctorplus.utils.Constants.ADD_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_PATIENT
import com.iotric.doctorplus.utils.Constants.DELETE_REPORT
import com.iotric.doctorplus.utils.Constants.GET_DOCTOR
import com.iotric.doctorplus.utils.Constants.UPDATE_PATIENT
import retrofit2.Call
import retrofit2.http.*
import java.lang.reflect.Type

interface ApiService {

    @GET(GET_DOCTOR)
     fun getDoctor()

    @POST(ADD_DOCTOR)
    @Headers("Content-Type:application/json")
    fun addDoctor(@Body doctor: Doctor): Call<DoctorResponse>

    @POST(ADD_PATIENT)
    fun addPatient()

    @POST(DELETE_PATIENT)

    fun deletePatient()

    @PUT(UPDATE_PATIENT)
    fun updatePatient()

    @DELETE(DELETE_REPORT)
    fun deletReport()
}