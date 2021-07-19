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
import com.iotric.doctorplus.viewmodel.UploadPatientReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class UploadPatientReportFragment : BaseFragment() {

    val viewModel: UploadPatientReportViewModel by viewModels()
    lateinit var binding: UploadPatientReportFragmentBinding
    var selectedImage: Uri? = null
    val args: UploadPatientReportFragmentArgs by navArgs()
    lateinit var repoName: String
    lateinit var reportDate: String

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

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.upload_report_fragment_title)
    }


    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.addReportButton.setOnClickListener {
            pickImage()
        }
        binding.uploadReport.setOnClickListener {
            viewModel.getUploadReportApi(reportUpload(),requireActivity().application)
        }
    }


    private fun initObserver() {
        viewModel.uploadReport.observe(requireActivity(), {

            it?.let {
                toastMessage("${it.message}")
                findNavController().popBackStack()
            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {
            toastMessage("${it}")
        })
    }

    private fun reportUpload(): MultipartBody.Part {
        /*  val multipartBody = MultipartBody.Part
          if (selectedImage == null) {
              snackBar("Select An Image", binding.root)
          } else {
              if (validateFields()) {*/
        /*  val reportName: RequestBody =
          RequestBody.create("reportname".toMediaTypeOrNull(), file.name)*/
        val file = File(selectedImage?.path)
        val requestbody = RequestBody.create("images".toMediaTypeOrNull(),file.name)
        val patientId: RequestBody =
            RequestBody.create("patientid".toMediaTypeOrNull(), args.patientId.id!!)
        val dateofreport: RequestBody =
            RequestBody.create("dateofreport".toMediaTypeOrNull(), reportDate)
        return MultipartBody.Part.createFormData(
            "images", file.name, requestbody)

    }


    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        repoName = binding.reportName.text.toString()
        reportDate = binding.reportDate.text.toString().trim()

        if (repoName.isEmpty()) {
            binding.layoutReportName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutReportName.setError(null)
        }
        if (reportDate.isEmpty()) {
            binding.layoutReoprtDate.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutReoprtDate.setError(null)

        return isAllFieldValidate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData()!!
            val file = File(selectedImage?.path).absolutePath

            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImage)
                binding.image.setImageURI(selectedImage)
                binding.reportName.setText(file)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                selectedImage = data.extras?.get("data") as Uri
                val file = File(selectedImage?.path).absolutePath
                binding.reportName.setText(file)
                binding.image.setImageURI(selectedImage)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
