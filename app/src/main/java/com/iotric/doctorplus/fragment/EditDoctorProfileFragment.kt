package com.iotric.doctorplus.fragment

import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.EditDoctorProfileFragmentBinding
import com.iotric.doctorplus.model.request.UpdateDoctorRequest
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.EditDoctorProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditDoctorProfileFragment : BottomSheetDialogFragment(){

    val viewModel: EditDoctorProfileViewModel by viewModels()
    lateinit var binding: EditDoctorProfileFragmentBinding
    val args: EditDoctorProfileFragmentArgs by navArgs()
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var speciality: String
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var address: String
    lateinit var sharePref: SharedPreferences

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
        initListener()
        initObserver()
        setArgs()
    }

    private fun initView() {
        binding.appbar.toolbar.navigationIcon?.setVisible(false,true)
        binding.appbar.toolbarTitle.text = getString(R.string.edit_doctor_profile)

    }

    private fun initObserver() {
        viewModel.updateDoctorProfile.observe(requireActivity(), Observer {
            it?.let {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.updateErrorMessage.observe(requireActivity(), Observer {
            it?.let {
                Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setArgs() {
        val args = args.EditDocResult
        binding.editName.setText(args.doctorname)
        binding.editEmail.setText(args.email)
        binding.editPhone.setText(args.phone)
        binding.editAddress.setText(args.adddress?.firstOrNull())
        binding.editSpecialization.setText(args.type)
    }

    private fun initListener() {
       /* binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }*/
        binding.editStartTime.setOnClickListener {
            startTimePicker()
        }

        binding.editEndTime.setOnClickListener {
            endTimePicker()
        }
        binding.btnSave.setOnClickListener {
                EditDoctor()

        }
    }

    private fun EditDoctor() {
        if (validateFields()) {
            val clinicHr = startTime + " - " + endTime
            val doctor = UpdateDoctorRequest(
                doctorname = name,
                phone = phone,
                type = speciality,
                clinichours = clinicHr, adddress = address)
            viewModel.getUpdateApi(doctor, requireActivity().application)
            findNavController().navigate(R.id.action_Drprofile_fragment)
        } else
            Toast.makeText(requireContext(), getString(R.string.mendatory_field_message), Toast.LENGTH_SHORT).show()
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
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
        if (phone.isEmpty()) {
            binding.layoutEditPhone.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditPhone.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditPhone.setError(null)

       /* if (email.isEmpty()) {
            binding.layoutEditEmail.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false

        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError(getString(R.string.invalid_email_message))
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)
*/
        if (speciality.isEmpty()) {
            binding.layoutEditSpecialization.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditSpecialization.setError(null)

        /*if (endTime.isEmpty()) {
            binding.layoutEditEndTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditEndTime.setError(null)

        if (startTime.isEmpty()) {
            binding.layoutEditClinicHr.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditClinicHr.setError(null)
*/
         if (address.isEmpty()) {
             binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditAddress.setError(null)
        return isAllFieldValidate
    }

    private fun startTimePicker() {
        UtilClass.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr = hourOfDay
                    min = minute
                    Log.i("start time", "$hourOfDay: $minute")
                    val time = UtilClass.time(hr, min)
                    binding.editStartTime.setText(time)
                }
            }, hr, min, false)
        timePickerDialog.show()
    }

    private fun endTimePicker() {
        UtilClass.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr1 = hourOfDay
                    min1 = minute
                    Log.i("End time", "$hourOfDay: $minute")
                    val time = UtilClass.time(hr1, min1)
                    binding.editEndTime.setText(time)
                }
            }, hr1, min1, false)

        timePickerDialog.show()
    }
}