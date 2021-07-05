package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.UploadPatientReportFragmentBinding
import com.iotric.doctorplus.model.response.PatientsItems
import com.iotric.doctorplus.networks.MultipartParams
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import com.iotric.doctorplus.viewmodel.UploadPatientReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class UploadPatientReportFragment : BaseFragment() {

    val viewModel: UploadPatientReportViewModel by viewModels()
    lateinit var binding: UploadPatientReportFragmentBinding
    lateinit var uri: Uri
    lateinit var patientId: String
    val args:UploadPatientReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UploadPatientReportFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.editImage.setOnClickListener {
            pickImage()
        }
        binding.uploadReport.setOnClickListener {
            reportUploadRequest()
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        val id = args.patientId.id
        patientId = binding.editPatientId.text.toString()
        binding.editPatientId.setText(id)
    }

    private fun initObserver() {
        viewModel.uploadReport.observe(requireActivity(), {
            dismissLoading()
            it?.let {
                snackBar("${it.message}", binding.root)

            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {
            dismissLoading()
            toastMessage("${it}")
        })
    }
    private fun reportUploadRequest() {
        val multipartParams = MultipartParams.Builder()
        val filePath = File(uri?.path)
        val patient = multipartParams.addFile("images", filePath).add("patientid", patientId)
        viewModel.getUploadReportApi(patient, requireActivity().application)
        /* val patient: RequestBody = MultipartBody.Builder()
             .setType(MultipartBody.FORM)
             .addFormDataPart("patientid", patientId)
             .addFormDataPart("images", filePath.toString())
             .build()
         viewModel.getUploadReportApi(patient, requireActivity().application)*/
    }

    private fun pickImage() {
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

    private fun checkRequestPermission(): Boolean {
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

    private fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, 2)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_PICK).also {
            it.type = "image/*,image/png,image/pdf"
            val mimeTypes =
                arrayOf("image/jpg", "image/png", "image/pdf", "image/doc", "image/jpeg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData()!!
            val file = File(uri.path)

            try {
                //val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.editImage.setText(file.name)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                uri = data.extras?.get("data") as Uri
                val file = File(uri.path)
                binding.editImage.setText(file.name)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
