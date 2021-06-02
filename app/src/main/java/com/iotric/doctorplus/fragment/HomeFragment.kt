package com.iotric.doctorplus.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.HomeFragmentPagerAdapter
import com.iotric.doctorplus.databinding.HomeFragmentBinding
import com.iotric.doctorplus.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding:HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  HomeFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.home))
        initView()
    }

    private fun initView() {
        binding.viewPager.adapter = HomeFragmentPagerAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
        binding.tablayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFF00"))
    }
}