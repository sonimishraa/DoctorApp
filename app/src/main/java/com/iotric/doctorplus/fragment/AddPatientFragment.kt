package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPatientFragment : Fragment() {

    private lateinit var viewModel: AddPatientViewModel
    private lateinit var binding: AddPatientFragmentBinding

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
            insertDatatoDatabase()
        }
       binding.appbar.toolbarTitle.text = getString(R.string.add_patient_toolbar_title)
        binding.appbar.toolbar.setNavigationOnClickListener {view ->
            findNavController().popBackStack()
        }

    }

    private fun insertDatatoDatabase() {
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
    }

}