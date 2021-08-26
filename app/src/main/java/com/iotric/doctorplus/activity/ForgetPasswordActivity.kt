package com.iotric.doctorplus.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ForgetPasswordActivityBinding
import com.iotric.doctorplus.model.request.ChangePasswordRequest
import com.iotric.doctorplus.model.request.OtpVerificationRequest
import com.iotric.doctorplus.viewmodel.ForgetPasswordViewModel

class ForgetPasswordActivity : BaseActivity() {

    lateinit var binding: ForgetPasswordActivityBinding
    val viewModel: ForgetPasswordViewModel by viewModels()
    lateinit var number: String
    lateinit var code: String
    lateinit var newPassword: String
    lateinit var phNumber:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgetPasswordActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initListener()
        initObsever()
    }

    private fun initListener() {
        binding.layoutVerifyOtp.visibility = View.VISIBLE
        binding.layoutResetPassword.visibility = View.GONE
        binding.btVerifyOtp.setOnClickListener {
            verifyOtp()
        }

    }

    private fun verifyOtp() {
        if (validateFields()) {
            val otpVerificationReq = OtpVerificationRequest(phone = number, code)
            viewModel.verifyOtpApi(otpVerificationReq, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)

    }

    private fun initObsever() {
        viewModel.verifyOtp.observe(this, {
            it?.let {
                toastMessage("${it.message}")
                binding.layoutVerifyOtp.visibility = View.GONE
                binding.layoutResetPassword.visibility = View.VISIBLE
                binding.btResetPassword.setOnClickListener {
                    resetPassword()
                }
            }
        })
        viewModel.changePassword.observe(this, {
            it?.let {
                toastMessage("${it.message}")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })

    }

    private fun resetPassword() {
        if (validatefield2()) {
            val changePasswordReq = ChangePasswordRequest(phone = phNumber, newPassword)
            viewModel.changePasswordApi(changePasswordReq, application)
        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        number = binding.editNumber.text.toString().trim()
        code = binding.editCode.text.toString().trim()

        if (number.isEmpty()) {
            binding.layoutNumber.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (number.length < 10) {
            binding.layoutNumber.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else {
            binding.layoutNumber.setError(null)
        }
        if (code.isEmpty()) {
            binding.layoutCode.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutCode.setError(null)

        return isAllFieldValidate
    }

    private fun validatefield2(): Boolean {
        var isAllFieldValidate = true
        phNumber = binding.editPhNumber.text.toString().trim()
        newPassword = binding.editNewPassword.text.toString().trim()

        if (phNumber.isEmpty()) {
            binding.layoutPhNumber.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phNumber.length < 10) {
            binding.layoutPhNumber.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else {
            binding.layoutPhNumber.setError(null)
        }
        if (newPassword.isEmpty()) {
            binding.layoutNewPassword.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutNewPassword.setError(null)

        return isAllFieldValidate
    }


}