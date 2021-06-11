package com.iotric.doctorplus.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iotric.doctorplus.databinding.ActivitySignUpBinding
import com.iotric.doctorplus.fragment.BaseActivity
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.viewmodel.RegisterDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

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
        initObserver()
    }

    private fun initObserver() {
        viewModel.addDoctorErrorMessage.observe(this, {
            Log.i("Error Message", "${it}")
            if(it != null){
                showMessage("${it}", binding.root)
            }
        })

        viewModel.addDoctorLiveData.observe(this, {
            Log.i("Succellfully created ", "${it}")
            if (it != null) {
                Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else
                Toast.makeText(this, "Please fill All the Mandatory Field", Toast.LENGTH_SHORT).show()
        })

    }

    private fun initView() {
        setActionBar(binding.toolbar)
        binding.btnAddDoctor.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        if (validateFields()) {
            val doctor = DoctorRegisterRequest(
                password = password,
                address = address,
                phone = phone,
                name = name,
                email = email
            )
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
}


