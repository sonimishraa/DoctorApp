package com.iotric.doctorplus.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object DateTimeUtil {

    private val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val LOCAL_FORMAT = "dd-MM-yyyy HH:mm"

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

   /* fun getSizeFormat(item: ResultsItem): String {
        val size = item.size?.toDouble()
        val sizeMB = String.format("%.1f", (size!! / (1024 * 1024)))
        return sizeMB.toDouble().toString()
    }

    fun getDurationTime(item: ResultsItem): String {
        val totalSecond = item.duration?.toLong()?.or(0)
        val hh = totalSecond!! / 3600
        val mm = (totalSecond % 3600) / 60
        val ss = (totalSecond % 60)
        var str = ""

        if (hh > 0) {
            str = "${
                hh
            }:${String.format("%02d", mm)}:${String.format("%02d", ss)}"
        } else {
            str = "${String.format("%02d", mm)}:${String.format("%02d", ss)}"
        }
        return str
    }*/
}
