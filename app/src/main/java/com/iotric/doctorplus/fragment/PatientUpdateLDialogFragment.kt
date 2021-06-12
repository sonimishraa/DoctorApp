package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentPatientUpdateBinding
import com.iotric.doctorplus.viewmodel.PatientListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientUpdateLDialogFragment : BottomSheetDialogFragment() {

    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var age: String

    private lateinit var binding: FragmentPatientUpdateBinding
    val viewModel: PatientListViewModel by viewModels()
    // private val args by navArgs<PatientUpdateLDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        // initView(view)
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_update_toolbar_title)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.btnUpdate.setOnClickListener {
            if (validateFields()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.profile_update_message),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editContact.text.toString().trim()
        age = binding.editAge.text.toString().trim()

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
            binding.layoutEditContact.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditContact.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditContact.setError(null)

        if (age.isEmpty()) {
            binding.layoutEditAge.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAge.setError(null)

        return isAllFieldValidate
    }
}