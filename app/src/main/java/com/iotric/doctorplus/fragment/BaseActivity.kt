package com.iotric.doctorplus.fragment

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.iotric.doctorplus.activity.HomeActivity


abstract class BaseActivity : AppCompatActivity() {
    fun snackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}