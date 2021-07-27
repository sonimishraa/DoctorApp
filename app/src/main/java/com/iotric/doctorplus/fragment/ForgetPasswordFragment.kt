package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.databinding.ForgetPasswordFragmentBinding
import com.iotric.doctorplus.viewmodel.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    lateinit var binding: ForgetPasswordFragmentBinding
    val viewModel: ForgetPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgetPasswordFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        initObsever()
    }

    private fun initview() {
        binding.btForget.setOnClickListener {
            val action = ForgetPasswordFragmentDirections.actionLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun initObsever() {

    }
}