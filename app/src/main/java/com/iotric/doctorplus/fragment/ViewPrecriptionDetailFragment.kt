package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentViewReportBinding
import com.iotric.doctorplus.databinding.ViewPrecriptionDetailFragmentBinding
import com.iotric.doctorplus.viewmodel.ViewPrecriptionDetailViewModel

class ViewPrecriptionDetailFragment : BaseFragment() {

    lateinit var binding: ViewPrecriptionDetailFragmentBinding
    val args: ViewPrecriptionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewPrecriptionDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        args.prescripItem.image?.forEach {
                Glide.with(requireContext()).load(it).into(binding.ivReport)
            }
        }
}