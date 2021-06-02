package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.databinding.AddDoctorFragmentBinding
import com.iotric.doctorplus.viewmodel.RegisterDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDoctorFragment : BaseFragment() {

    private lateinit var viewModel: RegisterDoctorViewModel
    private lateinit var binding: AddDoctorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddDoctorFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        //initViewModel()
    }

    private fun initView() {
        binding.btnAddDoctor.setOnClickListener {
            // createUser()
        }

    }

    private fun createUser() {
        /* val doctor = Doctor("", binding.editName.text.toString(),
             binding.editEmail.text.toString(),
             binding.editPhone.text.toString(),
             binding.editPassword.text.toString(),binding.editAddress.text.toString() )
         viewModel.getApiRequest(doctor)*/
    }

/*
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterDoctorViewModel::class.java)
        viewModel.addNewDoctor().observe(requireActivity(), Observer {
            if (it == null) {
                Toast.makeText(requireContext(), "Failed to add Doctor", Toast.LENGTH_SHORT).show()
            } else {
                //binding.editName.text =
                Toast.makeText(requireContext(), "Successfully Created", Toast.LENGTH_SHORT).show()


            }

        })
    }*/


}