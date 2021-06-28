package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.WeeklyAppointmentAdapter
import com.iotric.doctorplus.databinding.WeeklyAppointmentFragmentBinding
import com.iotric.doctorplus.viewmodel.WeeklyAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeeklyAppointmentFragment : BaseFragment() {

    val viewModel: WeeklyAppointmentViewModel by viewModels()
    lateinit var binding: WeeklyAppointmentFragmentBinding
    lateinit var adapter: WeeklyAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeeklyAppointmentFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        showLoading()
        adapter = WeeklyAppointmentAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.getAppointmentApi(requireActivity().application)
        dismissLoading()
    }

    private fun initObserver() {
        viewModel.getWeeklyAppoint.observe(requireActivity(), Observer {
            it?.let {
                adapter.submitList(it.data)
            }
        })
    }
}