package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.model.request.RegisterPatientRequest
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPatientFragment : BaseFragment() {

    val viewModel: AddPatientViewModel by viewModels()
    private lateinit var binding: AddPatientFragmentBinding
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var address: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddPatientFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        binding.btnAdd.setOnClickListener {
            registerPatient()
        }
        binding.appbar.toolbarTitle.text = getString(R.string.add_patient_toolbar_title)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

    private fun registerPatient() {
        if (validateFields()) {
            val patient = RegisterPatientRequest(name= name, email= email, phone= phone, password = password)
            viewModel.getApiResponse(patient, requireActivity().application)

        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun initObserver() {
        viewModel.registerPatientError.observe(requireActivity(), {
            Log.i("Error Message", "${it}")
            if (it != null) {
                snackBar("${it}", binding.root)
            }
        })

        viewModel.registerPatientItem.observe(requireActivity(), {
            Log.i("Succellfully created ", "${it}")
            toastMessage(getString(R.string.registered_profile_message))
            if (it != null) {
                findNavController().popBackStack()
            }
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        address = binding.editAddress.text.toString().trim()

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
             binding.layoutEditAddress.setError("Field Can't be Empty")
             isAllFieldValidate = false
         } else binding.layoutEditAddress.setError(null)*/

        return isAllFieldValidate
    }
}