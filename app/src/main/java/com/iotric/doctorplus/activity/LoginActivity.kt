package com.iotric.doctorplus.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ChangePasswordDialogueBinding
import com.iotric.doctorplus.databinding.LoginActivityBinding
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.ForgetPasswordOtpRequest
import com.iotric.doctorplus.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: LoginActivityBinding
    val viewModel by viewModels<LoginActivityViewModel>()
    lateinit var binding1: ChangePasswordDialogueBinding
    lateinit var alertDialogue: AlertDialog
    lateinit var userid: String
    lateinit var password: String
    lateinit var editNumber: String

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
        binding1 = ChangePasswordDialogueBinding.inflate(layoutInflater)
        val view = binding1.root
        builder.setCancelable(false)
        builder.setView(view)
        alertDialogue = builder.create()
        alertDialogue.show()
        val btnCancel = binding1.btCancel
        val btnSend = binding1.btSend

        btnSend.setOnClickListener {
            sendOtpRequest()
        }
        btnCancel.setOnClickListener {
            alertDialogue.dismiss()
        }
        alertDialogue.setCanceledOnTouchOutside(true)
    }

    private fun sendOtpRequest() {
        if (validateDialogue()) {
            val otpRequest = ForgetPasswordOtpRequest(phone = editNumber)
            viewModel.getOtpApi(otpRequest, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
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
                startActivity(Intent(this, MainActivity::class.java))
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
                //toastMessage("${it.verification?.sendCodeAttempts}")
                val intent = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(intent)
                alertDialogue.dismiss()
            }
        })
        viewModel.apiErrorMessage.observe(this, {
            alertDialogue.dismiss()
            snackBar("${it}", binding.root)
        })
    }

    private fun loginDoctor() {
        if (validateFields()) {
            val loginRequest = DoctorLoginRequest(userid, password)
            viewModel.fetchLoginRequest(loginRequest, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        userid = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        if (userid.isEmpty()) {
            binding.layoutEditNumber.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        }  else {
            binding.layoutEditNumber.setError(null)
        }
        if (password.isEmpty()) {
            binding.layoutEditPassword.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditPassword.setError(null)

        return isAllFieldValidate
    }

    private fun validateDialogue(): Boolean {
        var isAllFieldValidate = true
        editNumber = binding1.editNumber.text.toString().trim()

        if (editNumber.isEmpty()) {
            binding1.layoutEditNumber.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (editNumber.length < 10) {
            binding1.layoutEditNumber.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else {
            binding1.layoutEditNumber.setError(null)
        }
        return isAllFieldValidate
    }
}