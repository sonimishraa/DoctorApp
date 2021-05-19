package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.iotric.doctorplus.R


open class DashboardFragments : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("MEDICAL RECORDS")
        initView(view)
    }

    private fun initView(view: View) {
        drawerLayout = view.findViewById(R.id.drawer_layout)
        val patients = view.findViewById<LinearLayoutCompat>(R.id.lLayoutPatient)
        val addPatients = view.findViewById<LinearLayoutCompat>(R.id.lLayoutAdd_patient)
        val patientsRecords = view.findViewById<LinearLayoutCompat>(R.id.lLayout_patient_record)

        patients.setOnClickListener {
            findNavController().navigate(R.id.action_patient_list)
        }
        addPatients.setOnClickListener {
            findNavController().navigate(R.id.action_add_patient)
        }
        patientsRecords.setOnClickListener {
            findNavController().navigate(R.id.action_patient_record_Fragment)
        }
        val nav_view = view.findViewById<NavigationView>(R.id.nav_view)

        if (drawerLayout != null) {
            nav_view.setNavigationItemSelectedListener(this)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.close()
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