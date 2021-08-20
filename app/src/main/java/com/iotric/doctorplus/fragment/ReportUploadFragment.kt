package com.iotric.doctorplus.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.drjacky.imagepicker.ImagePicker
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentReportUploadBinding
import com.iotric.doctorplus.util.FileUtil
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.UploadPatientReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

@AndroidEntryPoint
class ReportUploadFragment : BaseFragment() {
    var hr = 0
    var min = 0
    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0
    var year = 0
    var month = 0
    var day = 0

    val viewModel: UploadPatientReportViewModel by viewModels()
    lateinit var binding: FragmentReportUploadBinding
    val args: ReportUploadFragmentArgs by navArgs()
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var repoName: String
    lateinit var reportDate: String
    lateinit var multiPartImageBody: MultipartBody.Part

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportUploadBinding.inflate(layoutInflater)
        return binding.root
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
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.addReportButton.setOnClickListener {
            imagepick()
        }
        binding.reportDate.setOnClickListener {
            pickDate()
        }
        binding.uploadReport.setOnClickListener {
            showLoading()
            if (validateFields()) {
                dismissLoading()
                reportUpload()
            }
        }
    }
    private fun initObserver() {
        viewModel.uploadReport.observe(requireActivity(), {
            dismissLoading()
            it?.let {
                toastMessage("${it.message}")
                findNavController().popBackStack()
            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {
            dismissLoading()
            Log.i("UploadImage", "Error Message:${it}")
            toastMessage("${it}")
        })
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

        if (::multiPartImageBody.isInitialized.not()) {
            isAllFieldValidate = false
        }
        return isAllFieldValidate
    }

    private fun imagepick() {
        ImagePicker.with(requireActivity())
            .crop()
            .cropOval()
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .createIntentFromDialog { launcher.launch(it) }

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                binding.image.setImageURI(uri)
                setImageUriOnPick(uri)
            }
        }

    private fun setImageUriOnPick(uri: Uri) {
        val body = FileUtil.selectFileName(requireContext(), uri)
        body?.let {
            multiPartImageBody = it
        }
    }

    private fun reportUpload() {
        // add another part within the multipart request
        val fullName =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.reportName.text.toString()
            )
        val patientId =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                args.patientId.id.orEmpty()
            )
        val date =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.reportDate.text.toString()
            )
        viewModel.getUploadReportApi(
            multiPartImageBody,
            fullName,
            patientId,
            date,
            requireActivity().application
        )
    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        val now = calendar.timeInMillis
        datePickerDialog =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    pickYear = year
                    pickMonth = month + 1
                    pickDay = dayOfMonth
                    val date = UtilClass.makeDateString(pickYear, pickMonth, pickDay)
                    binding.reportDate.setText(date)
                }
            }, year, month, day)
        datePickerDialog.datePicker.minDate = now
        datePickerDialog.show()
    }
}