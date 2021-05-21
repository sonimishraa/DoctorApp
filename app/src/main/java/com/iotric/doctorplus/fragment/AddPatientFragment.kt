package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel

class AddPatientFragment : BaseFragment() {

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
        setToolbarTitle(getString(R.string.add_record))
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)

    }

    private fun initView() {
        binding.btnAdd.setOnClickListener {
            insertDatatoDatabase()
            findNavController().popBackStack()
        }
    }

    private fun insertDatatoDatabase() {
        val name = binding.tvName.text.toString()
        val contact = binding.tvContact.text.toString()
        val date = binding.tvDate.text.toString()
        val user = User(name, contact, date)
        viewModel.insertUser(user)
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.successful_message),
            Toast.LENGTH_SHORT
        ).show()
    }

}