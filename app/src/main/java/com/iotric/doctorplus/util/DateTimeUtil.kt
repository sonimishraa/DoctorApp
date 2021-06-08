package com.iotric.doctorplus.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object DateTimeUtil {

    private val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val LOCAL_FORMAT = "dd-MMM-yyyy HH:mm"

    fun getSimpleDateFromUtc(date: String?): String? {
        val f: DateFormat = SimpleDateFormat(UTC_FORMAT)
        f.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            val d = f.parse(date)
            val formatedDate: DateFormat = SimpleDateFormat(LOCAL_FORMAT)
            formatedDate.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
