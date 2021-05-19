package com.iotric.doctorplus.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.HomeFragmentPagerAdapter
import com.iotric.doctorplus.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("HOME")
        initView(view)
    }

    private fun initView(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tablayout)
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = HomeFragmentPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFF00"))
    }
}