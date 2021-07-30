package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.AlignmentSpan
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.activity.LoginActivity
import com.iotric.doctorplus.util.UtilClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val title = SpannableString("Logout?")
        title.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            title.length,
            0
        )
        builder.setTitle(title)
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "You are Logged Out", Toast.LENGTH_SHORT).show()
                getLoginScreen()

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                // Toast.makeText(context, "Cancel Button Cliked", Toast.LENGTH_SHORT).show()
            })

        return builder.create()
    }

    private fun getLoginScreen() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                getString(R.string.share_pref),
                Context.MODE_PRIVATE
            )
        val authToken = sharedPreferences.getString("authToken", "")
        Log.i("LogoutFragment", "authTokenbefore:${authToken}")
        sharedPreferences.edit().clear().commit()
        Log.i("LogoutFragment", "authTokenafter:${authToken}")
        val intent = Intent(requireContext(),LoginActivity::class.java)
        startActivity(intent)
    }
}