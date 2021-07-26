package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.iotric.doctorplus.databinding.ViewPatientReportFragmentBinding
import com.iotric.doctorplus.viewmodel.ViewPatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPatientReportFragment : BaseFragment() {
    val viewModelView: ViewPatientRecordViewModel by viewModels()
    lateinit var binding: ViewPatientReportFragmentBinding
    val args: ViewPatientReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = ViewPatientReportFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        initObserver()
    }

    private fun initview() {
        val patientId = args.patientId.id
        viewModelView.getPatientReportApi(patientId,requireActivity().application)
    }

    private fun initObserver() {
        showLoading()
       viewModelView.patientRecord.observe(viewLifecycleOwner,{
           dismissLoading()
           it.report?.firstOrNull()?.let {
               it.labreports?.firstOrNull()?.let {
                   it.images?.firstOrNull()?.let {
                      Glide.with(requireContext()).load("http://3.108.56.211/reports/1627289798632-hello.jpg").into(binding.patientReport)
                   }
               }
           }
       })
    }
}