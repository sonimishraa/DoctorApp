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
            chooseImage()
            alertDialoge.cancel()
        }
        ivCamera.setOnClickListener {
            if (checkRequestPermission())
                dispatchTakePictureIntent()
            alertDialoge.cancel()
        }
        alertDialoge.setCanceledOnTouchOutside(true)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
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
            dispatchTakePictureIntent()

        } else
            toastMessage(getString(R.string.permission_granted_message))


    }

    // another method for camera

    private fun takePictureFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            context?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


// second permission

fun isPermissionsAllowed(
    permissions: Array<String>,
    shouldRequestIfNotAllowed: Boolean = false,
    requestCode: Int = -1
): Boolean {
    var isGranted = true

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (permission in permissions) {
            isGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
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
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        )
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
