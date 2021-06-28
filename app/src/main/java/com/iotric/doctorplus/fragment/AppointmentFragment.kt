package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.AppointmentPagerAdapter
import com.iotric.doctorplus.adapter.HomeFragmentPagerAdapter
import com.iotric.doctorplus.databinding.FragmentAppointentBinding
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.AppointmentFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointentBinding

    val viewModel: AppointmentFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.appointment_toolbar_title)
        binding.viewPager.adapter = AppointmentPagerAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
        binding.tablayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFF00"))
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }
}