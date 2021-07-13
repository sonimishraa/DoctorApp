package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DashboardFragmentBinding
import com.iotric.doctorplus.databinding.DashboardFragmentItemBinding
import com.iotric.doctorplus.model.response.PatientsItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragments : Fragment() {
   /* private lateinit var binding: DashboardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashboardFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        if (binding.drawerLayout != null) {
            binding.navView.setNavigationItemSelectedListener(this)
        }
    }

    private fun initListener() {
       *//* binding.dashboardFrame.lLayoutPatient.setOnClickListener {
            findNavController().navigate(R.id.action_patientList_fragment)
        }
        binding.dashboardFrame.lLayoutAddPatient.setOnClickListener {
            findNavController().navigate(R.id.action_add_patient)
        }
        binding.dashboardFrame.lLayoutPatientAppoitments.setOnClickListener {
            val action = DashboardFragmentsDirections.actionAppointmentFragment()
            findNavController().navigate(action)
        }
        binding.dashboardFrame.lLayoutPatientCloseCase.setOnClickListener {
             val action = DashboardFragmentsDirections.actionCloseCaseFragment()
            findNavController().navigate(action)
        }*//*
        binding.dashboardFrame.menubar.setOnClickListener {
            val drawer = binding.drawerLayout
            drawer.open()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.close()
        val id = item.itemId
        when (id) {
            R.id.navigation_profile -> {
                findNavController().navigate(R.id.action_profile_fragment)

            }
            R.id.navigation_appointment -> {
                findNavController().navigate(R.id.action_appointment_fragment)

            }
            R.id.navigation_privacy -> {
                findNavController().navigate(R.id.action_privacy_fragment)
            }
            R.id.navigation_logout -> {
                findNavController().navigate(R.id.action_logout_fragment)
            }
        }
        return true

    }
*/
}