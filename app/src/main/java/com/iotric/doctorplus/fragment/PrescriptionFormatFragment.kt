package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PrescriptionFormatFragmentBinding
import com.iotric.doctorplus.viewmodel.PrescriptionFormatViewModel

class PrescriptionFormatFragment : Fragment() {
    lateinit var binding:PrescriptionFormatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PrescriptionFormatFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }

}