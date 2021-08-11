package com.iotric.doctorplus.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPrescripFragmentBinding
import com.iotric.doctorplus.util.FileUtil
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.AddPrescripViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

class AddPrescripFragment : BaseFragment() {
    var hr = 0
    var min = 0
    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0
    var year= 0
    var month = 0
    var day = 0

    lateinit var binding: AddPrescripFragmentBinding
    val viewModel: AddPrescripViewModel by viewModels()
    val args: AddPrescripFragmentArgs by navArgs()
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var prescripName: String
    lateinit var description: String
    lateinit var multiPartImageBody: MultipartBody.Part


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddPrescripFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = "ADD NEW PRESCRIP"

    }
    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.addPrescripButton.setOnClickListener {
            pickImage()
        }
        binding.uploadPrescrip.setOnClickListener {
            if (validateFields()) {
                reportUpload()
            }
        }
    }

    private fun initObserver() {
        viewModel.uploadPrescrip.observe(requireActivity(), {
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

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        prescripName = binding.prescripName.text.toString()
        description = binding.description.text.toString().trim()

        if (prescripName.isEmpty()) {
            binding.layoutPrescripName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutPrescripName.setError(null)
        }
        if (description.isEmpty()) {
            binding.layoutDescription.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutDescription.setError(null)

        if (::multiPartImageBody.isInitialized.not()) {
            isAllFieldValidate = false
        }
        return isAllFieldValidate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Result for Image Selection
            data?.data?.let {
                setImageUriOnPick(it)
                binding.image.setImageURI(it)
            } ?: toastMessage("image invalid selection")
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
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.prescripName.text.toString())
        val patientId =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                args.patientId.id.orEmpty()
            )
        val description =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.description.text.toString())
        viewModel.getUploadPrescripApi(
            multiPartImageBody,
            fullName,
            patientId,
            description,
            requireActivity().application
        )
    }

   /* private fun pickDate() {
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
                    binding.prescripDate.setText(date)
                }
            }, year, month, day)
        datePickerDialog.datePicker.minDate = now
        datePickerDialog.show()
    }
*/


}