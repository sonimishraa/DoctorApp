package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.adapter.ViewPrescripAdapter
import com.iotric.doctorplus.databinding.ViewPrescripFragmentBinding
import com.iotric.doctorplus.model.response.Data
import com.iotric.doctorplus.model.response.PrecriptionItem
import com.iotric.doctorplus.viewmodel.ViewPrescripViewModel

class ViewPatientPrescripFragment : BaseFragment() {

    val viewModelView: ViewPrescripViewModel by viewModels()
    lateinit var binding: ViewPrescripFragmentBinding
    lateinit var viewPrescripAdapter: ViewPrescripAdapter
    val args: ViewPatientPrescripFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPrescripFragmentBinding.inflate(layoutInflater)
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
        viewPrescripAdapter =
            ViewPrescripAdapter(object : ViewPrescripAdapter.ItemClickListener {
                override fun onImageViewClick(item: PrecriptionItem) {
                   /* val action = ViewPatientPrescripFragmentDirections.actionViewPatientPrescripFragmentToPrescriptionFragment(item)
                    findNavController().navigate(action)*/
                }

                override fun onDeleteClick(item: PrecriptionItem) {
                    val patientId = args.patientId.id
                    val reportId = item.id
                    if (reportId != null) {
                        viewModelView.deleteReportApi(
                            patientId,
                            reportId,
                            requireActivity().application
                        )
                    }
                }

            })
        binding.recyclerView.adapter = viewPrescripAdapter
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
        viewModelView.patientPrescrip.observe(viewLifecycleOwner, {
            dismissLoading()
                viewPrescripAdapter.submitList(it.data.precription)
        })
        viewModelView.getErrorMessage.observe(viewLifecycleOwner, {
            dismissLoading()
            snackBar("${it}", binding.root)
        })

        viewModelView.deleteReport.observe(viewLifecycleOwner, {
            dismissLoading()
            toastMessage("${it.message}")
        })
    }
}