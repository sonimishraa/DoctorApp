package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.ViewModel


class AppointmentFragmentViewModel : ViewModel() {

    fun makeDateString(year: Int, month: Int, day: Int): String {
        val date = "$day/${monthFormat(month)}/$year"
        return date
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
}
