package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ChangePasswordDialogueBinding
import com.iotric.doctorplus.databinding.LoginFragmentBinding
import com.iotric.doctorplus.model.request.DoctorLoginRequest
import com.iotric.doctorplus.model.request.ForgetPasswordOtpRequest
import com.iotric.doctorplus.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    val viewModel: LoginActivityViewModel by viewModels()
    lateinit var binding: LoginFragmentBinding
    lateinit var binding1: ChangePasswordDialogueBinding
    lateinit var alertDialogue: AlertDialog
    lateinit var number: String
    lateinit var password: String
    lateinit var editNumber:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        binding.toolbarTitle.text = getString(R.string.login_toolbar_title)

        binding.btnSignIn.setOnClickListener {
            loginDoctor()
        }
        binding.tvNewRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionSignUpFragment()
            findNavController().navigate(action)
        }
        binding.forgetPassword.setOnClickListener {
            openAlertDialogue()
        }
    }

    private fun openAlertDialogue() {
        val builder = AlertDialog.Builder(requireContext())
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
    }

    private fun sendOtpRequest() {
        if (validateDialogue()) {
            val otpRequest = ForgetPasswordOtpRequest(phone = editNumber)
            viewModel.getOtpApi(otpRequest, requireActivity().application)
        }else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun initObserver() {
        viewModel.loginData.observe(viewLifecycleOwner, {
            Log.i("authToken ", "${it?.authToken}}")
            Log.i("id ", "${it?.id}}")
            if (it != null) {
                // Save data into sharedPref
                val sharedPreferences = activity?.getSharedPreferences(
                    getString(R.string.share_pref),
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("authToken", it.authToken)
                editor.putString("DoctorID", it.id)
                editor.apply()
              val action = LoginFragmentDirections.actionDashboardFragment()
                findNavController().navigate(action)
            } else {
                snackBar(getString(R.string.login_fail_message), binding.root)
            }
        })
        viewModel.loginError.observe(viewLifecycleOwner, {

            it?.let {
                snackBar("${it}", binding.root)
            }
        })
        viewModel.forgetPasswordOtp.observe(viewLifecycleOwner, {
            alertDialogue.dismiss()
            it?.let {
                //toastMessage("${it.verification?.sendCodeAttempts}")
                val action = LoginFragmentDirections.actionForgetPassword()
                findNavController().navigate(action)
            }
        })
        viewModel.apiErrorMessage.observe(viewLifecycleOwner,{
            alertDialogue.dismiss()
            snackBar("${it}",binding.root)
        })
    }

    private fun loginDoctor() {
        if (validateFields()) {
            val loginRequest = DoctorLoginRequest(number, password)
            viewModel.fetchLoginRequest(loginRequest, requireActivity().application)
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