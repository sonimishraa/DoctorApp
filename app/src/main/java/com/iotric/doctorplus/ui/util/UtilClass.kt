package com.iotric.doctorplus.ui.util

import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan

class UtilClass {

    fun alterDialogPosition(){
        val title = SpannableString("Logout?")
        title.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            title.length,
            0)
    }
}