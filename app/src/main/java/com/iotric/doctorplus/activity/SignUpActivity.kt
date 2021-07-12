package com.iotric.doctorplus.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import com.iotric.doctorplus.R
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
            if (it != null) {
                snackBar("${it}", binding.root)
            }
        })

        viewModel.addDoctorLiveData.observe(this, {
            if (it != null) {
                toastMessage("${it.message}")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun initView() {
        setActionBar(binding.toolbar)
        binding.btnAddDoctor.setOnClickListener {
            createUser()
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        if (validateFields()) {
            val doctor = DoctorRegisterRequest(
                password = password,
                phone = phone,
                name = name,
                email = email
            )
            viewModel.getApiResponse(doctor, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        //address = binding.editAddress.text.toString().trim()

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
            binding.layoutEditPhone.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditPhone.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditPhone.setError(null)

        if (password.isEmpty()) {
            binding.layoutEditPassword.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditPassword.setError(null)

       /* if (address.isEmpty()) {
            binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)
*/
        return isAllFieldValidate
    }
}


