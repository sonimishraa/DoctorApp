package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DashboardFragmentBinding


open class DashboardFragments : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: DashboardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DashboardFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.mediacal_records))
        initView()
    }

    private fun initView() {
        binding.dashboardFrame.lLayoutPatient.setOnClickListener {
            findNavController().navigate(R.id.action_patient_list)
        }
        binding.dashboardFrame.lLayoutAddPatient.setOnClickListener {
            findNavController().navigate(R.id.action_add_patient)
        }
        binding.dashboardFrame.lLayoutPatientRecord.setOnClickListener {
            findNavController().navigate(R.id.action_patient_record_Fragment)
        }
        val drawer = binding.drawerLayout
        if (drawer != null) {
            binding.navView.setNavigationItemSelectedListener(this)
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

}