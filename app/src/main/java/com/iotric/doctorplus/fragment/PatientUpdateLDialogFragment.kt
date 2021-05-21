package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentPatientUpdateBinding
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel

class PatientUpdateLDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPatientUpdateBinding

    private val args by navArgs<PatientUpdateLDialogFragmentArgs>()

    lateinit var viewModel: AddPatientViewModel

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
        initView(view)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)
    }

    private fun initView(view:View) {
        binding.appbar.toolbarTitle.text = resources.getString(R.string.update_detail)
        binding.editName.setText(args.CurrentUser.name)
        binding.editContact.setText(args.CurrentUser.contact)
        binding.editDate.setText(args.CurrentUser.date)
        binding.btnUpdate.setOnClickListener {
            updateDetail()
        }
    }

    private fun updateDetail() {
        val name = binding.editName.text.toString()
        val contact = binding.editContact.text.toString()
        val date = binding.editDate.text.toString()
        val user = User(name, contact, date)
        viewModel.updateUser(user)
        Toast.makeText(requireContext(), getString(R.string.successful_message), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

}