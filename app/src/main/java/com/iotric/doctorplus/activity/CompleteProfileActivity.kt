package com.iotric.doctorplus.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.CompleteProfileActivityBinding
import com.iotric.doctorplus.model.request.UpdateDoctorRequest
import com.iotric.doctorplus.viewmodel.EditDoctorProfileViewModel

class CompleteProfileActivity : BaseActivity() {
    val viewModel: EditDoctorProfileViewModel by viewModels()
    lateinit var binding: CompleteProfileActivityBinding
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var speciality: String
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var address: String
    lateinit var sharePref: SharedPreferences
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

    private fun initObserver() {
        binding.appbar.toolbar.setNavigationOnClickListener {

        }
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

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            EditDoctor()
        }
    }

    private fun EditDoctor() {
        if (validateFields()) {
            //val clinicHr = startTime + " - " + endTime
            val doctor = UpdateDoctorRequest(
                doctorname = name,
                phone = phone,
                type = speciality,
                clinicstarttime = startTime,
                clinicendtime = endTime,
                adddress = address
            )
            viewModel.getUpdateApi(doctor, application)
        } else
            Toast.makeText(this, getString(R.string.mendatory_field_message), Toast.LENGTH_SHORT)
                .show()
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

        /*  if (endTime.isEmpty()) {
              binding.layoutEditEndTime.setError(getString(R.string.empty_field_message))
              isAllFieldValidate = false
          } else binding.layoutEditEndTime.setError(null)
          if (startTime.isEmpty()) {
              binding.layoutEditClinicHr.setError(getString(R.string.empty_field_message))
              isAllFieldValidate = false
          } else binding.layoutEditClinicHr.setError(null)*/

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)
        return isAllFieldValidate
    }

}
