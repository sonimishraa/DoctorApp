package com.iotric.doctorplus.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.iotric.doctorplus.databinding.ActivitySignUpBinding
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.viewmodel.RegisterDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var address: String

    val viewModel by viewModels<RegisterDoctorViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initViewModel()
    }

    @SuppressLint("UseSupportActionBar")
    private fun initView() {
        setActionBar(binding.toolbar)
        binding.btnAddDoctor.setOnClickListener {
            validateFields()
            createUser()
        }
    }

    private fun createUser() {
        if (validateFields()) {
            val doctor = DoctorRegisterRequest(name, email, phone, password, address)
            viewModel.getApiResponse(doctor, application)
        } else
            Toast.makeText(this, "Please fill All the Mandatory Field", Toast.LENGTH_SHORT).show()

    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        address = binding.editAddress.text.toString().trim()

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

        if (phone.isEmpty()) {
            binding.layoutEditPhone.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditPhone.setError(" 10 Number Digit Require")
            isAllFieldValidate = false
        } else binding.layoutEditPhone.setError(null)

        if (password.isEmpty()) {
            binding.layoutEditPassword.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditPassword.setError(null)

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }


    private fun initViewModel() {
        viewModel.addDoctorLiveData.observe(this, {
            if (it != null) {
                Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to create", Toast.LENGTH_SHORT).show()
            }
        })
    }

}


