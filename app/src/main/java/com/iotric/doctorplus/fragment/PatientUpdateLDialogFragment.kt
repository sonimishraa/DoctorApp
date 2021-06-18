package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentPatientUpdateBinding
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.util.UtilClass.day
import com.iotric.doctorplus.util.UtilClass.month
import com.iotric.doctorplus.util.UtilClass.year
import com.iotric.doctorplus.viewmodel.PatientUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientUpdateLDialogFragment : BottomSheetDialogFragment() {

    var hr = 0
    var min = 0
    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0

    lateinit var name: String
    lateinit var report: String
    lateinit var phone: String
    lateinit var address: String
    lateinit var email: String
    lateinit var nextVisit: String
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var datePickerDialog:DatePickerDialog

    private lateinit var binding: FragmentPatientUpdateBinding
    val viewModel: PatientUpdateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initObserver() {

    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_update_toolbar_title)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.editNextAppointmentTime.setOnClickListener {
            pickAppointmentTime()
        }
        binding.editNextAppointmentDate.setOnClickListener {
            pickDate()
        }
        binding.btnUpdate.setOnClickListener {
            if (validateFields()) {
                updatePatient()
                findNavController().popBackStack()
            }
        }
    }

    private fun updatePatient() {
        if (validateFields()) {
            val updatePatient = UpdatePatientRequest(pname = name, pphone = phone)
            viewModel.getUpdateApi(updatePatient, requireActivity().application)
        }
    }

    private fun validateFields(): Boolean {
        val str = "first Name"
        val str1 = "Last Name"
        val result = str + " " + str1
        val result1 = "${str} ${str1}"
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        report = binding.editReport.text.toString().trim()
        phone = binding.editContact.text.toString().trim()
        address = binding.editAddress.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        nextVisit = binding.editNextAppointmentTime.toString().trim()
        if (name.isEmpty()) {
            binding.layoutEditName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }
        if (email.isEmpty()) {
            binding.layoutEditEmail.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError(getString(R.string.invalid_email_message))
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)
        if (phone.isEmpty()) {
            binding.layoutEditContact.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditContact.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditContact.setError(null)

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }

    private fun pickAppointmentTime() {
        UtilClass.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr = hourOfDay
                    min = minute
                    Log.i("start time", "$hourOfDay: $minute")
                    val time = UtilClass.time(hr, min)
                    binding.editNextAppointmentTime.setText(time)
                }
            }, hr, min, false)
        timePickerDialog.show()
    }

    private fun pickDate() {
        UtilClass.getDateCalendarInstance()
        datePickerDialog =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    pickYear = year
                    pickMonth = month + 1
                    pickDay = dayOfMonth
                    val date = UtilClass.makeDateString(pickYear, pickMonth, pickDay)
                    binding.editNextAppointmentDate.setText(date)
                }
            }, year, month, day)
        datePickerDialog.show()
    }

}