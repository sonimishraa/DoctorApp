package com.iotric.doctorplus.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.R
import com.iotric.doctorplus.ui.viewmodel.LabTestViewModel

class LabTestFragment : BaseFragment() {

    private lateinit var viewModel: LabTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lab_test_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle("LAB TESTS")
        viewModel = ViewModelProvider(this).get(LabTestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}