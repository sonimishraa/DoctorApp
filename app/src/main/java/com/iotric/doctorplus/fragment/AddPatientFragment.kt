package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.model.request.AddPatientRequest
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddPatientFragment : BaseFragment() {

    val viewModel: AddPatientViewModel by viewModels()
    private lateinit var binding: AddPatientFragmentBinding
    lateinit var image: String
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var address: String
    lateinit var age: String
    lateinit var gender: String
    lateinit var bloodgroup: String
    lateinit var symptoms: String

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
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.add_patient_toolbar_title)
    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.btnCancle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            showLoading()
            if (validateFields()){
                registerPatient()
            }
        }
        binding.autotextGender.setOnClickListener {
            showDropDown()
        }
    }

    private fun registerPatient() {
            /* val multipartParams = MultipartParams.Builder()
             //val filePath = File(uri?.path)
             val patient = multipartParams.add("patientname", name)
                 .add("phone", phone).add("address", address).add("email", email).add("age", age)
                 .add("gender", gender)*/
            val patient = AddPatientRequest(
                patientname = name,
                email = email,
                phone = phone,
                gender = gender,
                age = age,
                bloodgroup = bloodgroup,
                symptoms = symptoms
            )
            viewModel.getApi(patient, requireActivity().application)
       /* else {
            snackBar(getString(R.string.mendatory_field_message), binding.root)
        }*/
    }

    private fun initObserver() {
        viewModel.registerPatientError.observe(requireActivity(), {
            dismissLoading()
            Log.i("Error Message", "${it}")
            if (it != null) {
                snackBar("${it}", binding.root)
                findNavController().popBackStack()
            }
        })

        viewModel.registerPatientItem.observe(requireActivity(), {
            dismissLoading()
            toastMessage(it.message.toString())
            view?.post {
                val action = AddPatientFragmentDirections.actionAddfragmentToPatientList()
                findNavController().popBackStack()
            }
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        image = binding.editUploadPris.text.toString().trim()
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        age = binding.editAge.text.toString().trim()
        //address = binding.editAddress.text.toString().trim()
        gender = binding.autotextGender.text.toString().trim()
        bloodgroup = binding.editBloodgroup.text.toString().trim()
        symptoms = binding.editSymptoms.text.toString().trim()

        if (age.isEmpty()) {
            binding.layoutEditAge.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditAge.setError(null)
        }
        if (gender.isEmpty()) {
            binding.layoutEditGender.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditGender.setError(null)

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

        return isAllFieldValidate
    }

    private fun showDropDown() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.gender_dropdown_item, gender)
        binding.autotextGender.setAdapter(arrayAdapter)
    }
}