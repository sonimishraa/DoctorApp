package com.iotric.doctorplus.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.LoginActivityBinding
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.ForgetPasswordOtpRequest
import com.iotric.doctorplus.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

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
        initObserver()
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
        binding.forgetPassword.setOnClickListener {
            openAlertDialogue()
        }
    }

    private fun openAlertDialogue() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogueView = inflater.inflate(R.layout.change_password_dialogue, null)
        builder.setCancelable(false)
        builder.setView(dialogueView)
        val alertDialogue = builder.create()
        alertDialogue.show()
        val  btnCancel= dialogueView.findViewById<Button>(R.id.bt_cancel)
        val btnSend = dialogueView.findViewById<Button>(R.id.bt_send)

        btnSend.setOnClickListener {
            sendOtpRequest()
        }
        btnCancel.setOnClickListener {
            alertDialogue.dismiss()
        }
    }

    private fun sendOtpRequest() {
        if (validateFields()) {
            val otpRequest = ForgetPasswordOtpRequest(phone = number)
            viewModel.getOtpApi(otpRequest,application)
        }
    }

    private fun initObserver() {
        viewModel.loginData.observe(this, Observer {
            Log.i("authToken ", "${it?.authToken}}")
            Log.i("id ", "${it?.id}}")
            if (it != null) {
                // Save data into sharedPref
                val sharedPreferences = getSharedPreferences(
                    getString(R.string.share_pref),
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("authToken", it.authToken)
                editor.putString("DoctorID", it.id)
                editor.apply()
                startActivity(Intent(this, DrDashboardActivity::class.java))
                finish()
            } else {
                snackBar(getString(R.string.login_fail_message), binding.root)
            }
        })
        viewModel.loginError.observe(this, Observer {
            it?.let {
                snackBar("${it}", binding.root)
            }
        })
        viewModel.forgetPasswordOtp.observe(this, {
            it?.let {
                toastMessage("${it.verification?.sendCodeAttempts}")
                val intent = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun loginDoctor() {
        if (validateFields()) {
                val loginRequest = DoctorLoginRequest(number, password)
                viewModel.fetchLoginRequest(loginRequest, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        number = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        if (number.isEmpty()) {
            binding.layoutEditNumber.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (number.length < 10) {
            binding.layoutEditNumber.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else {
            binding.layoutEditNumber.setError(null)
        }

        if (password.isEmpty()) {
            binding.layoutEditPassword.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditPassword.setError(null)

        return isAllFieldValidate
    }
}