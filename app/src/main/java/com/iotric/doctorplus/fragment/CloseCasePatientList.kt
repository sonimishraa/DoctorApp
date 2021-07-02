package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.adapter.InActivePatientListAdapter
import com.iotric.doctorplus.databinding.ClosePatientListBinding
import com.iotric.doctorplus.databinding.PatientListFragmentBinding
import com.iotric.doctorplus.viewmodel.InActivePatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloseCasePatientList : BaseFragment() {
    val viewModel: InActivePatientViewModel by viewModels()
    lateinit var patientListAdapter: InActivePatientListAdapter
     //val args: CloseCasePatientListArgs  by navArgs()
    private lateinit var binding: ClosePatientListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ClosePatientListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
      /*  val patientId = args.result.id
        if (patientId != null) {
            viewModel.getClosePatientApi(patientId, requireActivity().application)
        }*/

    }

    private fun initObserver() {
        viewModel.closePatientList.observe(requireActivity(), {
            it.resp
        })
    }

}
