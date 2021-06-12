package com.iotric.doctorplus.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.EditDoctorProfileFragmentBinding
import com.iotric.doctorplus.viewmodel.EditDoctorProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditDoctorProfileFragment : Fragment() {

    val viewModel: EditDoctorProfileViewModel by viewModels()
    lateinit var binding: EditDoctorProfileFragmentBinding
    lateinit var name: String
    lateinit var email: String
    lateinit var speciality: String
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var address: String

    var hr = 0
    var min = 0
    var hr1 = 0
    var min1 = 0

    lateinit var timePickerDialog: TimePickerDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditDoctorProfileFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.edit_doctor_profile)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.editStartTime.setOnClickListener {
            startTimePicker()
        }

        binding.editEndTime.setOnClickListener {
            endTimePicker()
        }
        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.doctor_profile_edit_message),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.mendatory_field_message),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun EditDoctor() {
        /* if (validateFields()) {
             val doctor = EditDoctorRequest(name, email )
             viewModel.getApiResponse(doctor, requireActivity().application)
         } else
             Toast.makeText(requireContext(), "Please fill All the Mandatory Field", Toast.LENGTH_SHORT).show()
         viewModel.registerPatientItem.observe(viewLifecycleOwner, {
             if (it != null) {
                 Toast.makeText(requireContext(), "Successfully Created", Toast.LENGTH_SHORT).show()
             } else {
                 Toast.makeText(requireContext(), "Failed to create", Toast.LENGTH_SHORT).show()
             }
         })*/
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        speciality = binding.editSpecialization.text.toString().trim()
        address = binding.editAddress.text.toString().trim()
        startTime = binding.editStartTime.text.toString().trim()
        endTime = binding.editEndTime.text.toString().trim()
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

        if (speciality.isEmpty()) {
            binding.layoutEditSpecialization.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditSpecialization.setError(null)

        if (endTime.isEmpty()) {
            binding.layoutEditEndTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditEndTime.setError(null)

        if (startTime.isEmpty()) {
            binding.layoutEditStartTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditStartTime.setError(null)

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }

    private fun startTimePicker() {
        viewModel.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr = hourOfDay
                    min = minute
                    Log.i("start time", "$hourOfDay: $minute")
                    val time = viewModel.time1(hr, min)
                    binding.editStartTime.setText(time)
                }
            }, hr, min1, false)
        timePickerDialog.show()
    }

    private fun endTimePicker() {
        viewModel.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr1 = hourOfDay
                    min1 = minute
                    Log.i("End time", "$hourOfDay: $minute")
                    val time = viewModel.time1(hr1, min1)
                    binding.editEndTime.setText(time)
                }
            }, hr1, min1, false)

        timePickerDialog.show()
    }
}