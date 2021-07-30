package com.iotric.doctorplus.activity

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.CompleteProfileActivityBinding
import com.iotric.doctorplus.model.request.UpdateDoctorRequest
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.EditDoctorProfileViewModel

class CompleteProfileActivity : BaseActivity() {
    var hr = 0
    var min = 0
    var hr1 = 0
    var min1 = 0
    val viewModel: EditDoctorProfileViewModel by viewModels()
    lateinit var binding: CompleteProfileActivityBinding
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var hospital: String
    lateinit var experience: String
    lateinit var education: String
    lateinit var title: String
    lateinit var gender: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompleteProfileActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = "COMPLETE YOUR PROFILE"

    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener {
        }
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

    private fun initObserver() {
        viewModel.updateDoctorProfile.observe(this, {
            it?.let {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        viewModel.updateErrorMessage.observe(this, {
            it?.let {
                Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
            }
        })
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
                clinicendtime = endTime)
            viewModel.getUpdateApi(doctor, application)
        } else
            Toast.makeText(this, getString(R.string.mendatory_field_message), Toast.LENGTH_SHORT)
                .show()
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

        if (gender.isEmpty()) {
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
        } else binding.layoutEditHospital.setError(null)
        return isAllFieldValidate
    }
    private fun startTimePicker() {
        UtilClass.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
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
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
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
