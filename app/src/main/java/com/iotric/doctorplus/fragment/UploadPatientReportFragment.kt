package com.iotric.doctorplus.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.UploadPatientReportFragmentBinding
import com.iotric.doctorplus.networks.MultipartParams
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
            reportUpload()
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
            Log.i("UploadImage", "Error Message:${it}")
            toastMessage("${it}")
        })
    }

    private fun reportUpload() {
        if (selectedImage == null) {
            snackBar("Select An Image", binding.root)
        } else {
            if (validateFields()) {
                val imageFile = File(selectedImage!!.path)
                val fileName = imageFile.name
                val reqBody: RequestBody =
                    RequestBody.create("multipart/form-file".toMediaTypeOrNull(), imageFile)
                val partImage =
                    MultipartBody.Part.createFormData("file", imageFile.name, reqBody)

              /*  val file: RequestBody = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    imageFile
                )
*/
                val fname: RequestBody = RequestBody.create(
                    "reportname".toMediaTypeOrNull(),
                    fileName
                )

                val id: RequestBody = RequestBody.create(
                    "patientid".toMediaTypeOrNull(),
                    args.patientId.id!!
                )

                val date: RequestBody = RequestBody.create(
                    "dateofreport".toMediaTypeOrNull(),
                    reportDate
                )

                viewModel.getUploadReportApi(partImage, fname, id, date, requireActivity().application)
            }
        }
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
