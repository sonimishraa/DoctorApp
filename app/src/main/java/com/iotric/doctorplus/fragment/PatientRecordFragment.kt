package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatientReportAdapter
import com.iotric.doctorplus.databinding.PatientRecordFragmentBinding
import com.iotric.doctorplus.databinding.PatientRecordFragmentsBinding
import com.iotric.doctorplus.viewmodel.PatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientRecordFragment : BaseFragment() {

    val viewModel: PatientRecordViewModel by viewModels()
    val args: PatientRecordFragmentArgs by navArgs()
    lateinit var patientReportAdapter: PatientReportAdapter
    private lateinit var binding: PatientRecordFragmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = PatientRecordFragmentsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        setArgs()
        initObserver()
    }
    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_record)
        val id = args.result.id
        patientReportAdapter = PatientReportAdapter()
        //binding.recyclerView.adapter = patientReportAdapter
        if(id != null){
            viewModel.getPatientReportApi(id, requireActivity().application)
        }



      /*  binding.toolbar.inflateMenu(R.menu.main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_new_report -> {

                }
                R.id.update_report -> {

                }
            }
            true
        }*/
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

    private fun setArgs() {
        val argsItem = args.result
        val visitItem = argsItem.visit?.firstOrNull()
        binding.tvName.text = argsItem.pname
        binding.tvContact.text = argsItem.pphone
        binding.tvEmail.text = argsItem.pemail
       // binding.tvLastVisit.text = visitItem?.nextvisitdate + " " + visitItem?.nextvisittime ?: ""
    }

    private fun initObserver() {
        showLoading()
        viewModel.patientRecord.observe(requireActivity(), {
            dismissLoading()
            it.report?.forEach {
                //patientReportAdapter.submitList(it?.labreports)
            }
        })
        viewModel.getErrorMessage.observe(requireActivity(), {
            dismissLoading()
            snackBar("${it}", binding.root)
        })
    }



}

