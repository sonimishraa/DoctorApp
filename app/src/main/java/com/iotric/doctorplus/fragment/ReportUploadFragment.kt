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
import com.iotric.doctorplus.databinding.FragmentReportUploadBinding
import com.iotric.doctorplus.databinding.UploadPatientReportFragmentBinding
import com.iotric.doctorplus.networks.MultipartParams
import com.iotric.doctorplus.viewmodel.UploadPatientReportViewModel
import java.io.File
import java.io.IOException


class ReportUploadFragment : BaseFragment() {

    val viewModel: UploadPatientReportViewModel by viewModels()
    lateinit var binding: FragmentReportUploadBinding
    var selectedImage: Uri? = null
    val args: UploadPatientReportFragmentArgs by navArgs()
    lateinit var repoName: String
    lateinit var reportDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportUploadBinding.inflate(layoutInflater)
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
        if (validateFields()) {
            val multipartParams = MultipartParams.Builder()
            val filePath = File(selectedImage?.path)
            val patient =
                multipartParams.addFile("images", filePath).add("patientid", args.patientId.id)
                    .add("dateofreport", reportDate).add("reportname", filePath.name)

            viewModel.getreportUloadApi(patient, requireActivity().application)
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