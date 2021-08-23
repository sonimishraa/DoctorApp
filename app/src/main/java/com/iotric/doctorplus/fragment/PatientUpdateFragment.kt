package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentPatientUpdateBinding
import com.iotric.doctorplus.model.request.UpdatePatientRequest
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.util.UtilClass.day
import com.iotric.doctorplus.util.UtilClass.month
import com.iotric.doctorplus.util.UtilClass.year
import com.iotric.doctorplus.viewmodel.PatientUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientUpdateFragment : BaseFragment() {

    lateinit var name: String
    lateinit var report: String
    lateinit var phone: String
    lateinit var age: String
    lateinit var email: String
    lateinit var healthIssue:String

    private lateinit var binding: FragmentPatientUpdateBinding
    val args: PatientUpdateFragmentArgs by navArgs()
    val viewModel: PatientUpdateViewModel by viewModels()

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
        initListener()
        initObserver()
        setArgs()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_update_toolbar_title)
    }

    private fun initObserver() {
        viewModel.updateError.observe(requireActivity(), Observer {
            if (it != null) {
                Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.updatePatientProfile.observe(requireActivity(), Observer {
            if (it != null) {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                view?.post {
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun setArgs() {
        val argsItem = args.result
        Log.i("PatientUpdateFragment", "${argsItem}")
        binding.editName.setText(argsItem.pname.orEmpty())
        binding.editContact.setText(argsItem.pphone.orEmpty())
        binding.editAge.setText(argsItem.age)
        binding.healthIssue.setText(argsItem.symtoms?.firstOrNull())
    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.btnCancle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSave.setOnClickListener {
            updatePatient()
        }

    }
    private fun updatePatient() {
        if (validateFields()) {
            val id = args.result.id ?: ""
            val updatePatient = UpdatePatientRequest(
                pname = name, pphone = phone, age = age, healthIssue = healthIssue )
            viewModel.getUpdateApi(id, updatePatient, requireActivity().application)

        } else
            Toast.makeText(
                requireContext(),
                getString(R.string.mendatory_field_message),
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString()
        phone = binding.editContact.text.toString()
        age = binding.editAge.text.toString()
        healthIssue = binding.healthIssue.text.toString()

        if (name.isEmpty()) {
            binding.layoutEditName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }
        if (phone.isEmpty()) {
            binding.layoutEditContact.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditContact.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditContact.setError(null)

        if (age.isEmpty()) {
            binding.layoutEditAddress.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        if (healthIssue.isEmpty()) {
            binding.layoutHealthIssue.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutHealthIssue.setError(null)

        return isAllFieldValidate
    }

}