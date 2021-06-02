package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.iotric.doctorplus.util.UtilClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val title = SpannableString("Logout?")
        title.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            title.length,
            0)
        builder.setTitle(title)
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "Yes Button Cliked", Toast.LENGTH_SHORT).show()

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "Cancel Button Cliked", Toast.LENGTH_SHORT).show()
            })

        return builder.create()
    }
}