package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.iotric.doctorplus.R
import com.iotric.doctorplus.util.FileUtil
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseFragment : Fragment() {
    lateinit var alertDialoge: AlertDialog
    lateinit var currentPhotoPath: String

    fun showLoading() {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.custom_progressbar, null)
        val dialogColor = ColorDrawable(Color.BLACK);
        dialogColor.setAlpha(0); //(0-255) 0 means fully transparent, and 255 means fully opaque
        builder.setCancelable(false)
        builder.setView(dialogeView)
        alertDialoge = builder.create()
        alertDialoge.window?.setBackgroundDrawable(dialogColor)
        alertDialoge.show()
    }

    fun dismissLoading() {
      alertDialoge.dismiss()
    }

    fun snackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    }

    fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    fun pickImage() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.pick_image_dialogue, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val ivCamera = dialogeView.findViewById<LinearLayoutCompat>(R.id.layout_camera)
        val ivImage = dialogeView.findViewById<LinearLayoutCompat>(R.id.Layout_gallery)
        ivImage.setOnClickListener {
            alertDialoge.cancel()
        }
        ivCamera.setOnClickListener {
            alertDialoge.cancel()
        }
        alertDialoge.setCanceledOnTouchOutside(true)
    }

}
