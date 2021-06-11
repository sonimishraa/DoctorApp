package com.iotric.doctorplus.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.LoginActivityBinding
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    val viewModel by viewModels<LoginActivityViewModel>()
    lateinit var number: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun loginDoctor() {
        if (validateFields()) {
            // TODO need to add number and passsword accroding to auth request
            val loginRequest = DoctorLoginRequest(number, password)
            viewModel.fetchLoginRequest(loginRequest, application)
            viewModel.loginData.observe(this, Observer {
                if (it != null) {
                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                    Log.i("authToken ", "${it.authToken}")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else
            Toast.makeText(this, "Please fill All the Mandatory Field", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        binding.toolbarTitle.text = getString(R.string.login_toolbar_title)
        binding.btnSignIn.setOnClickListener {
            loginDoctor()
        }
        binding.tvNewRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        number = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        if (number.isEmpty()) {
            binding.layoutEditNumber.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else if (number.length < 10) {
            binding.layoutEditNumber.setError(" 10 Number Digit Require")
            isAllFieldValidate = false
        } else {
            binding.layoutEditNumber.setError(null)
        }

        if (password.isEmpty()) {
            binding.layoutEditPassword.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditPassword.setError(null)

        return isAllFieldValidate
    }
}