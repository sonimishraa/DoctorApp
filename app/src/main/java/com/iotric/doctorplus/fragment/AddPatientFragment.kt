package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.model.request.DoctorRegisterRequest
import com.iotric.doctorplus.model.request.RegisterPatientRequest
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import com.iotric.doctorplus.viewmodel.AppointmentFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPatientFragment : Fragment() {

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
            val patient = RegisterPatientRequest(name, email, phone, password)
            viewModel.getApiResponse(patient, requireActivity().application)
        } else
            Toast.makeText(requireContext(), "Please fill All the Mandatory Field", Toast.LENGTH_SHORT).show()
        viewModel.registerPatientItem.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireContext(), "Successfully Created", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addfragment_to_patient_list)
            } else {
                Toast.makeText(requireContext(), "Failed to create", Toast.LENGTH_SHORT).show()
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

       /* if (address.isEmpty()) {
            binding.layoutEditAddress.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)*/

        return isAllFieldValidate
    }

    /*  private fun insertDatatoDatabase() {
          val name = binding.tvName.text.toString()
          val contact = binding.tvContact.text.toString()
          val date = binding.tvDate.text.toString()
          val user = User(name, contact, date)
          //viewModel.insertUser(user)
          Toast.makeText(
              requireContext(),
              resources.getString(R.string.successful_message),
              Toast.LENGTH_SHORT
          ).show()
      }*/

}