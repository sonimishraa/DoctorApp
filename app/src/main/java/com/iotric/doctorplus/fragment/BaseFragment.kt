package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.iotric.doctorplus.R

abstract class BaseFragment : Fragment() {
    lateinit var alertDialoge: AlertDialog

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
        val dialogeView = inflater.inflate(R.layout.alter_dialoge_profile, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val ivCamera = dialogeView.findViewById<AppCompatTextView>(R.id.ivCamera)
        val ivImage = dialogeView.findViewById<AppCompatTextView>(R.id.ivImage)
        val tv_cancel = dialogeView.findViewById<AppCompatTextView>(R.id.tv_cancel)
        val tv_ok = dialogeView.findViewById<AppCompatTextView>(R.id.tv_ok)

        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
        tv_ok.setOnClickListener {
            chooseImage()
            alertDialoge.cancel()
        }
        ivImage.setOnClickListener {
            chooseImage()
            alertDialoge.cancel()

        }
        ivCamera.setOnClickListener {
            if (checkRequestPermission())
                takePictureFromCamera()
            alertDialoge.cancel()
        }
    }

    fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, 2)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun chooseImage() {
        val intent = Intent()
        //intent.setType("image/*")
        intent.setAction(Intent.ACTION_PICK).also {
            it.type = "image/png"
            val mimeTypes =
                arrayOf("image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    fun checkRequestPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission =
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.CAMERA),
                    2
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()

        } else
            toastMessage(getString(R.string.permission_granted_message))
    }

}