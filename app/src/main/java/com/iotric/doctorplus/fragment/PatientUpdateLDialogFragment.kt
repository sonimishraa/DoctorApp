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
                Toast.makeText(requireContext(), "Successfully Updated Patient Profile", Toast.LENGTH_SHORT).show()
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
            binding.layoutEditContact.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditContact.setError(" 10 Number Digit Require")
            isAllFieldValidate = false
        } else binding.layoutEditContact.setError(null)

        if (age.isEmpty()) {
            binding.layoutEditAge.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAge.setError(null)

        return isAllFieldValidate
    }

    /*  private fun initView(view: View) {
          binding.appbar.toolbarTitle.text = resources.getString(R.string.update_detail)
          binding.editName.setText(args.CurrentUser.name)
          binding.editContact.setText(args.CurrentUser.contact)
          binding.editDate.setText(args.CurrentUser.date)
          binding.btnUpdate.setOnClickListener {
              updateDetail()
          }
      }
  */
    /* private fun updateDetail() {
         val name = binding.editName.text.toString()
         val contact = binding.editContact.text.toString()
         val date = binding.editDate.text.toString()
         val user = User(name, contact, date)
         viewModel.updateUser(user)
         Toast.makeText(requireContext(), getString(R.string.successful_message), Toast.LENGTH_SHORT)
             .show()
         findNavController().popBackStack()
     }
 */
}