package com.iotric.doctorplus.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import java.util.*

object UtilClass {

    var year = 0
    var month = 0
    var day = 0

    var hr = 0
    var min = 0

    fun alterDialogPosition(){
        val title = SpannableString("Logout?")
        title.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            title.length,
            0)
    }

    // get Calendar Instance for Date and and date formate

    fun makeDateString(year: Int, month: Int, day: Int): String {
        val date = "$day/${monthFormat(month)}/$year"
        return date
    }

    fun getDateCalendarInstance() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

    }

    private fun monthFormat(month: Int): String {
        if (month == 1)
            return "01"
        if (month == 2)
            return "02"
        if (month == 3)
            return "03"
        if (month == 4)
            return "04"
        if (month == 5)
            return "05"
        if (month == 6)
            return "06"
        if (month == 7)
            return "07"
        if (month == 8)
            return "08"
        if (month == 9)
            return "09"
        if (month == 10)
            return "10"
        if (month == 11)
            return "11"
        if (month == 12)
            return "12"

        return "01"
    }

    // for time get Calendar Instance and Time formate

    fun time(hr: Int, min: Int): String {
        val time1 = "$hr:$min"
        return time1
    }
    fun time2(hr: Int, min: Int): String {
        val time2 = "$hr:$min" + timeFormate(hr)
        return time2
    }

    fun timeFormate(hr: Int): String {
        if (hr <= 12)
            return "AM"
        if (hr > 12)
            return "PM"
        else
            return "AM"
    }

    fun getTimeCalender() {
        val calendar = Calendar.getInstance()
        hr = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
    }

    fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val cursor = query(uri,null,null,null,null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
}