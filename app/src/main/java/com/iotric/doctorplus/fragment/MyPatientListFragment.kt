package com.iotric.doctorplus.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.MyPatientPagerAdapter
import com.iotric.doctorplus.databinding.MyPatientListFragmentBinding
import com.iotric.doctorplus.viewmodel.MyPatientListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPatientListFragment : Fragment() {

    val viewModel: MyPatientListViewModel by viewModels()
    lateinit var binding: MyPatientListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyPatientListFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.my_patient_list_toolbar_title)
        //binding.viewPager.adapter = MyPatientPagerAdapter(childFragmentManager)
        //binding.tablayout.setupWithViewPager(binding.viewPager)
        binding.tablayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFF00"))
    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

}