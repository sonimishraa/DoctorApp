package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PatientPreExamineFragmentBinding
import com.iotric.doctorplus.viewmodel.PatientPreExamineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientPreExamineFragment : BaseFragment() {
    lateinit var binding: PatientPreExamineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PatientPreExamineFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }

}