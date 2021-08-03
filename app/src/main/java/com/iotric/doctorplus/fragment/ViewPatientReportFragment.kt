package com.iotric.doctorplus.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.adapter.PatientReportAdapter
import com.iotric.doctorplus.databinding.ViewPatientReportFragmentBinding
import com.iotric.doctorplus.model.response.ReportItem
import com.iotric.doctorplus.util.FileUtil
import com.iotric.doctorplus.viewmodel.ViewPatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPatientReportFragment : BaseFragment() {
    var myDownloadId: Long = 0
    val viewModelView: ViewPatientRecordViewModel by viewModels()
    lateinit var patientReportAdapter: PatientReportAdapter
    lateinit var binding: ViewPatientReportFragmentBinding
    val args: ViewPatientReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPatientReportFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        initListener()
        initObserver()
    }

    private fun initview() {
        binding.appbar.toolbarTitle.setText(" REPORTS")
        patientReportAdapter =
            PatientReportAdapter(object : PatientReportAdapter.ItemClickListener {
                override fun onImageViewClick(item: ReportItem) {
                   val action = ViewPatientReportFragmentDirections.actionViewPatientReportFragmentToViewReportFragment(item)
                    findNavController().navigate(action)
                }
            })
        binding.recyclerView.adapter = patientReportAdapter
        val patientId = args.patientId.id
        viewModelView.getPatientReportApi(patientId, requireActivity().application)
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        showLoading()
        viewModelView.patientRecord.observe(viewLifecycleOwner, {
            dismissLoading()
            it.let {
                patientReportAdapter.submitList(it.report)
            }
        })
    }
}