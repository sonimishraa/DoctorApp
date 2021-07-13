package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DashFragmentBinding
import com.iotric.doctorplus.databinding.DashboardFragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashFragment : Fragment() {
    lateinit var binding: DashboardFragmentItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashboardFragmentItemBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        binding.lLayoutPatient.setOnClickListener {
            findNavController().navigate(R.id.action_patientList_fragment)
        }
        binding.lLayoutAddPatient.setOnClickListener {
            findNavController().navigate(R.id.action_add_patient)
        }
        binding.lLayoutPatientAppoitments.setOnClickListener {
            val action = DashFragmentDirections.actionAppointmentFragment()
            findNavController().navigate(action)
        }
        binding.lLayoutPatientCloseCase.setOnClickListener {
            val action = DashFragmentDirections.actionCloseCaseFragment()
            findNavController().navigate(action)
        }
    }
}