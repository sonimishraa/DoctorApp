package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.iotric.doctorplus.databinding.FragmentViewReportBinding

class ReportDetailFragment : Fragment() {
    lateinit var binding: FragmentViewReportBinding
    val args: ReportDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        args.reportItem.labreports?.forEach {
            it?.images?.forEach {
                Glide.with(requireContext()).load(it).into(binding.ivReport)
            }
        }

    }
}