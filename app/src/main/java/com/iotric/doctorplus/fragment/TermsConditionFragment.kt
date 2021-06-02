package com.iotric.doctorplus.fragment

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentTermsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsConditionFragment : BaseFragment() {

    private lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      binding =  FragmentTermsBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setToolbarTitle(getString(R.string.menu_terms))
    }
}