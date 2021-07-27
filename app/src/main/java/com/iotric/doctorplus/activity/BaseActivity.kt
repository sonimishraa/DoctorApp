package com.iotric.doctorplus.activity

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.iotric.doctorplus.R


abstract class BaseActivity : AppCompatActivity() {

   lateinit var alertDialoge:AlertDialog
    fun showLoading() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.custom_progressbar, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        alertDialoge = builder.create()
        alertDialoge.show()
    }

    fun dismissLoading() {
        alertDialoge.dismiss()
    }


    fun snackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}