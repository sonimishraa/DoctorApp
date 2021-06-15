package com.iotric.doctorplus.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.iotric.doctorplus.activity.HomeActivity


abstract class BaseActivity : AppCompatActivity() {
    lateinit var mProgressDialog:ProgressDialog
     var isShowing:Boolean = false

    fun snackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading")
        mProgressDialog.setCancelable(false)
        mProgressDialog.isIndeterminate = true
    }
    fun showProgressDialog() {
        if(!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
}