package com.iotric.doctorplus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.LabReportFragmentAdapter
import com.iotric.doctorplus.databinding.FragmentLabReportBinding
import com.iotric.doctorplus.viewmodel.DailyAppointmentViewModel
import com.iotric.doctorplus.viewmodel.LabReportViewodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LabReportFragment : BaseFragment() {

    lateinit var binding: FragmentLabReportBinding
    val viewModel: LabReportViewodel by viewModels()
    lateinit var adapter: LabReportFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLabReportBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
    }

    private fun initview() {

    }


}