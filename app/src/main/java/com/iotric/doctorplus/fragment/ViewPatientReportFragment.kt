package com.iotric.doctorplus.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ViewPatientReportFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPatientReportFragment : BaseFragment() {

    lateinit var binding: ViewPatientReportFragmentBinding
    val args: ViewPatientReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = ViewPatientReportFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = "PATIENT REPORT"
        binding.appbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
       /* val image = args.patientId.labreports?.firstOrNull()?.images
        Glide.with(requireContext()).load(image).into(binding.patientReport)*/
    }


}