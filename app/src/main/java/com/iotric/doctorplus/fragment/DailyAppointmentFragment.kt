package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.DailyAppointmentAdapter
import com.iotric.doctorplus.databinding.DailyAppointmentFragmentBinding
import com.iotric.doctorplus.viewmodel.DailyAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DailyAppointmentFragment : BaseFragment() {

    val viewModel: DailyAppointmentViewModel by viewModels()
    lateinit var binding:DailyAppointmentFragmentBinding
    lateinit var adapter: DailyAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DailyAppointmentFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        adapter = DailyAppointmentAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.getAppointApi(requireActivity().application)
    }

    private fun initObserver() {
        viewModel.getDailyAppointment.observe(requireActivity(), Observer {
            Log.i("DailyFragment", "Success Message: ${it.message}")
            it.data?.let {
                adapter.submitList(it)
            }
        })

        viewModel.getErrorMessage.observe(requireActivity(), Observer {
            snackBar("${it}", binding.root)
        })
    }
}