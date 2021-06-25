package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    lateinit var nextVisitDate: String
    lateinit var nextVisitTime: String
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var datePickerDialog: DatePickerDialog

    private lateinit var binding: FragmentPatientUpdateBinding
    val args: PatientUpdateLDialogFragmentArgs by navArgs()
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
        initListener()
        initObserver()
        setArgs()
    }

    private fun initView() {
        binding.appbar.toolbar.navigationIcon?.setVisible(false,false)
        binding.appbar.toolbarTitle.text = getString(R.string.patient_update_toolbar_title)
    }

    private fun initObserver() {
        viewModel.updateError.observe(requireActivity(), Observer {
            if (it != null) {
                Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.updatePatientProfile.observe(requireActivity(), Observer {
            if (it != null) {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setArgs() {
        val argsItem = args.result
        val visitItem = argsItem.visit?.firstOrNull()
        Log.i("PatientUpdateFragment", "${argsItem}")
        binding.editName.setText(argsItem.pname.orEmpty())
        binding.editContact.setText(argsItem.pphone.orEmpty())
        binding.editAddress.setText(argsItem.address?.firstOrNull())
        binding.editNextAppointmentDate.setText(visitItem?.nextvisitdate)
        binding.editNextAppointmentTime.setText(visitItem?.nextvisittime)

    }

    private fun initListener() {
       /* binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }*/
        binding.editNextAppointmentTime.setOnClickListener {
            pickAppointmentTime()
        }
        binding.editNextAppointmentDate.setOnClickListener {
            pickDate()
        }
        binding.btnUpdate.setOnClickListener {
                updatePatient()
        }
    }

    private fun updatePatient() {
        if (validateFields()) {
            val id = args.result.id ?: ""
            val updatePatient = UpdatePatientRequest(
                pname = name, pphone = phone, address = address, nextVisitDate, nextVisitTime
            )
            viewModel.getUpdateApi(id, updatePatient, requireActivity().application)
            findNavController().navigate(R.id.action_patient_list)
        }else
            Toast.makeText(requireContext(), getString(R.string.mendatory_field_message), Toast.LENGTH_SHORT).show()
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString()
        report = binding.editReport.text.toString()
        phone = binding.editContact.text.toString()
        address = binding.editAddress.text.toString()
        email = binding.editEmail.text.toString()
        nextVisitDate = binding.editNextAppointmentDate.text.toString().trim()
        nextVisitTime = binding.editNextAppointmentTime.text.toString().trim()

        if (name.isEmpty()) {
            binding.layoutEditName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }
        /*if (email.isEmpty()) {
            binding.layoutEditEmail.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError(getString(R.string.invalid_email_message))
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)*/
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