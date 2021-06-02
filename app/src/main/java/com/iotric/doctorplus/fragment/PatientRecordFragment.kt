package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatientRecordPagerAdapter
import com.iotric.doctorplus.databinding.PatientRecordFragmentBinding
import com.iotric.doctorplus.viewmodel.PatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientRecordFragment : BaseFragment() {

    private lateinit var viewModel: PatientRecordViewModel
    private lateinit var binding:PatientRecordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PatientRecordFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("PAST RECORD")
        initView()
    }

    private fun initView() {
        val pagerAdapter = PatientRecordPagerAdapter(childFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        binding.tablayout.setupWithViewPager(viewPager)
    }

}

