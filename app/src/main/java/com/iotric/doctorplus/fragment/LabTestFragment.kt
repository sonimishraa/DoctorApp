package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.LabTestFragmentBinding
import com.iotric.doctorplus.viewmodel.LabTestViewModel
import com.iotric.doctorplus.viewmodel.PatientListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LabTestFragment : Fragment() {

    val viewModel: PatientListViewModel by viewModels()
    lateinit var binding: LabTestFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LabTestFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
    }

    private fun initview() {
        binding.appbar.toolbarTitle.text = getString(R.string.lab_test)
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

}