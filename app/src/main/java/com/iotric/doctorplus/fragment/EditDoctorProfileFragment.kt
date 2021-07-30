package com.iotric.doctorplus.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
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
class EditDoctorProfileFragment : BottomSheetDialogFragment() {

    val viewModel: EditDoctorProfileViewModel by viewModels()
    lateinit var binding: EditDoctorProfileFragmentBinding
    val args: EditDoctorProfileFragmentArgs by navArgs()
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var hospital: String
    lateinit var experience: String
    lateinit var education: String
    lateinit var title: String
    lateinit var gender: String


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
        binding.appbar.toolbar.navigationIcon?.setVisible(false, true)
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
        binding.editGender.setText(args.gender)
        binding.editHospital.setText(args.hospital)
        binding.editEducation.setText(args.education)
        binding.editExperience.setText(args.experience)
        binding.editTitle.setText(args.title)
        binding.editStartTime.setText(args.clinicstarttime)
        binding.editEndTime.setText(args.clinicendtime)
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.editStartTime.setOnClickListener {
            startTimePicker()
        }

        binding.editEndTime.setOnClickListener {
            endTimePicker()
        }
        binding.btnCancle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSave.setOnClickListener {
            EditDoctor()
        }
    }

    private fun EditDoctor() {
        if (validateFields()) {
            //val clinicHr = startTime + " - " + endTime
            val doctor = UpdateDoctorRequest(
                title = title,
                education = education,
                gender = gender,
                hospital = hospital,
                experience = experience,
                clinicstarttime = startTime,
                clinicendtime = endTime
            )
            viewModel.getUpdateApi(doctor, requireActivity().application)
            findNavController().navigate(R.id.action_Drprofile_fragment)
        } else
            Toast.makeText(
                requireContext(),
                getString(R.string.mendatory_field_message),
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        hospital = binding.editHospital.text.toString().trim()
        title = binding.editTitle.text.toString().trim()
        experience = binding.editExperience.text.toString().trim()
        education = binding.editEducation.text.toString().trim()
        gender = binding.editGender.text.toString().trim()
        startTime = binding.editStartTime.text.toString().trim()
        endTime = binding.editEndTime.text.toString().trim()

        /* if (gender.isEmpty()) {
             binding.layoutEditGender.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else {
             binding.layoutEditGender.setError(null)
         }
         if (title.isEmpty()) {
             binding.layoutEditTitle.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditTitle.setError(null)

         if (education.isEmpty()) {
             binding.layoutEditEducation.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditEducation.setError(null)

         if (endTime.isEmpty()) {
             binding.layoutEditEndTime.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditEndTime.setError(null)

         if (startTime.isEmpty()) {
             binding.layoutEditClinicHr.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditClinicHr.setError(null)

         if (hospital.isEmpty()) {
             binding.layoutEditHospital.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else binding.layoutEditHospital.setError(null)*/
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