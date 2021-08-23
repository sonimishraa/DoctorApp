package com.iotric.doctorplus.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DashboardFragmentBinding
import com.iotric.doctorplus.databinding.DashboardFragmentItemBinding
import com.iotric.doctorplus.model.response.GetDoctorByidResponse
import com.iotric.doctorplus.model.response.PatientsItems
import com.iotric.doctorplus.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class DashboardFragments : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: DashboardFragmentBinding
    lateinit var getDoctorId:GetDoctorByidResponse
    val viewModel: ProfileFragmentViewModel by viewModels()
    lateinit var profile:CircleImageView
    lateinit var name:AppCompatTextView

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
        initObserver()
    }

    private fun initView() {
        if (binding.drawerLayout != null) {
            binding.navView.setNavigationItemSelectedListener(this)
        }
        val headerView =  binding.navView.inflateHeaderView(R.layout.nav_header_main)
        profile = headerView.findViewById(R.id.profile_image)
        name= headerView.findViewById(R.id.name)
        val id = getDoctorId()
        viewModel.getDoctorApi(id, requireActivity().application)

    }

    private fun initListener() {
        binding.dashboardFrame.lLayoutPatient.setOnClickListener {
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
        }
        binding.dashboardFrame.menubar.setOnClickListener {
            val drawer = binding.drawerLayout
            drawer.open()
        }
    }

    private fun initObserver() {
        showLoading()
        val loginDrid = getDoctorId()
        Log.i("ProfileFragment", "_id:${id}")
        viewModel.getDoctorById.observe(viewLifecycleOwner, {
            getDoctorId = it
            getDoctorId.let {
                if ( it._id == loginDrid) {
                    //Glide.with(requireContext()).load(it.profilepic).into(profile)
                  name.text = it.doctorname
                }
            }
            dismissLoading()
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.close()
        val id = item.itemId
        when (id) {
            R.id.navigation_profile -> {
                findNavController().navigate(R.id.action_profile_fragment)

            }
            R.id.navigation_lab -> {
                findNavController().navigate(R.id.action_lab_fragment)

            }
            R.id.navigation_notifications -> {
                findNavController().navigate(R.id.action_notifications)

            }
            R.id.navigation_faq -> {
                findNavController().navigate(R.id.action_faq_fragment)

            }
            R.id.navigation_terms -> {
                findNavController().navigate(R.id.action_terms_Fragment)

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
    private fun getDoctorId(): String? {
        val sharePref = requireActivity().getSharedPreferences(
            getString(R.string.share_pref),
            Context.MODE_PRIVATE
        )
        val id = sharePref.getString("DoctorID", "")
        Log.i("ProfileFragment","${id}")
        return id
    }

}