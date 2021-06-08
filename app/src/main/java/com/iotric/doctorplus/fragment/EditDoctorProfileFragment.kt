package com.iotric.doctorplus.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.EditDoctorProfileFragmentBinding
import com.iotric.doctorplus.viewmodel.EditDoctorProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class EditDoctorProfileFragment : Fragment(), TimePickerDialog.OnTimeSetListener {

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
    var pickHr = 0
    var pickMin = 0
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
            timePicker()
        }

        binding.editEndTime.setOnClickListener {
            timePicker()
        }
        binding.btnSave.setOnClickListener {
            validateFields()
            EditDoctor()
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
            binding.layoutEditName.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }

        if (email.isEmpty()) {
            binding.layoutEditEmail.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError("Invalid Email Id")
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)

        if (speciality.isEmpty()) {
            binding.layoutEditSpecialization.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditSpecialization.setError(null)

        if (endTime.isEmpty()) {
            binding.layoutEditEndTime.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditEndTime.setError(null)

        if (startTime.isEmpty()) {
            binding.layoutEditStartTime.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditStartTime.setError(null)

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }

    private fun timePicker() {
        getTimeCalender()
        timePickerDialog = TimePickerDialog(requireContext(), this, hr, min, true)
        timePickerDialog.show()
    }

    private fun getTimeCalender() {
        val calendar = Calendar.getInstance()
        hr = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        pickHr = hr
        pickMin = min
        val time = viewModel.makeDateString(pickHr, pickMin)
        binding.editStartTime.setText(time)
    }
}