package com.iotric.doctorplus.utils

object Constants {

    const val BASE_URL = "http://3.7.124.53/api/"
    const val GET_DOCTOR_NUMBER = "doctor/getdoctorbynumber/{number}"
    const val UPDATE_DOCTOR_PROFILE = "doctor"
    const val GET_DOCTOR_ID = "doctor/{id}"
    const val REGISTER_DOCTOR = "doctor/registerdoctor"
    const val UPDATE_PATIENT = "patient/{id}"
    const val ADD_PATIENT = "patient/addpatient"
    const val ADD_PATIENT_REPORT = "report"
    const val CHANGE_PATIENT_STATUS = "patient/changestatus/{id}"
    const val CLOSE_CASE_PATIENT_LIST = "patient/myclosecase"
    const val REGISTER_PATIENT = "patient/addpatient"
    const val DELETE_PATIENT = "patient/{id}"
    const val GET_PATIENT_LIST = "doctor/getall"
    const val DELETE_REPORT = "report/{id}"
    const val DOCTOR_LOGIN = "doctor/doctorlogin"
    const val All_PATIENT_LIST = "patient"
    const val MY_PATIENT_LIST = "patient/mypatient"
    const val GET_WEEKLY_APPOINTMENT = "apointment/weekly"
    const val GET_DAILY_APPOINTMENT = "apointment/daily"
    const val ADD_NEW_APPOINTMENT = "apointment"
    const val UPDATE_APPOINTMENT = "apointment/{id}"
    const val DELETE_APPOINTMENT = "apointment/{id}"
    const val GET_PATIENT_REPORT_BY_ID = "report/patientreport/{id}"
    const val FORGET_PASSWORD_OTP = "doctor/sendotp"
    const val SEARCH_PATIENT = "patient/search"
    const val UPLOAD_IMAGE_SIZE = 10* 1024*1024
    const val ADD_PRESCRIPTION = "precription"
    const val GET_PRESCRIPTION_PATIENT_ID = "precription/{id}"
    const val CHANGE_DR_PROFILE = "doctor/changeprofilepic"
}