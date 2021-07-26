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
import androidx.core.content.ContextCompat
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
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        /*  intent.setAction(Intent.).also {
              it.type = "image/png"
              val mimeTypes =
                  arrayOf("image/png")
              it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
          }*/
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun checkRequestPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission =
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.CAMERA),
                    CAPTURE_IMAGE_REQUEST
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

        if (requestCode == CAPTURE_IMAGE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()

        } else
            toastMessage(getString(R.string.permission_granted_message))


    }

// second permission

    fun isPermissionsAllowed(permissions: Array<String>, shouldRequestIfNotAllowed: Boolean = false, requestCode: Int = -1): Boolean {
        var isGranted = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                isGranted = ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
                if (!isGranted)
                    break
            }
        }
        if (!isGranted && shouldRequestIfNotAllowed) {
            if (requestCode.equals(-1))
                throw RuntimeException("Send request code in third parameter")
            requestRequiredPermissions(permissions, requestCode)
        }

        return isGranted
    }

    fun requestRequiredPermissions(permissions: Array<String>, requestCode: Int) {
        val pendingPermissions: ArrayList<String> = ArrayList()
        permissions.forEachIndexed { index, permission ->
            if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED)
                pendingPermissions.add(permission)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val array = arrayListOf(pendingPermissions.size.toString())
            pendingPermissions.toArray(array.toArray())
            requestPermissions(array.toArray() as Array<out String>, requestCode)
        }
    }

    fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
        var isGranted = true
        for (grantResult in grantResults) {
            isGranted = grantResult.equals(PackageManager.PERMISSION_GRANTED)
            if (!isGranted)
                break
        }
        return isGranted
    }
}
