package com.iotric.doctorplus.util

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
        val date = "$day/$month/$year"
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
            return "JAN"
        if (month == 2)
            return "FEB"
        if (month == 3)
            return "MAR"
        if (month == 4)
            return "APR"
        if (month == 5)
            return "MAY"
        if (month == 6)
            return "JUN"
        if (month == 7)
            return "JUL"
        if (month == 8)
            return "AUG"
        if (month == 9)
            return "SEP"
        if (month == 10)
            return "OCT"
        if (month == 11)
            return "NOV"
        if (month == 12)
            return "DEC"

        return "JAN"
    }

    // for time get Calendar Instance and Time formate

    fun time(hr: Int, min: Int): String {
        val time1 = "$hr:$min"
        //+ timeFormate(hr)
        return time1
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
}