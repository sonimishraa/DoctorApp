package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.adapter.InActivePatientListAdapter
import com.iotric.doctorplus.databinding.ClosePatientListBinding
import com.iotric.doctorplus.model.response.CloseCasePatientItem
import com.iotric.doctorplus.viewmodel.InActivePatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloseCasePatientList : BaseFragment() {
    val viewModel: InActivePatientViewModel by viewModels()
    lateinit var closePatientListAdapter: InActivePatientListAdapter

    // val args: CloseCasePatientListArgs by navArgs()
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
        initListener()
    }

    private fun initView() {
        //binding.appbar.toolbarTitle.text = "INACTIVE PATIENT LIST"
        viewModel.getClosePatientApi(requireActivity().application)
        closePatientListAdapter =
            InActivePatientListAdapter(object : InActivePatientListAdapter.ItemClickListener {
                override fun onChangeStatus(result: CloseCasePatientItem) {
                    val id = result.id
                    if (id != null) {
                        viewModel.getStatusChangeApi(requireActivity().application,id)
                    }
                }
            })
        binding.recyclerView.adapter = closePatientListAdapter
    }

    private fun initListener() {
       /* binding.appbar.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }*/
    }

    private fun initObserver() {
        showLoading()
        viewModel.closePatientList.observe(requireActivity(), {
            dismissLoading()
            if (it.patient.isNullOrEmpty()) {
                binding.layoutNoitem.visibility = View.VISIBLE
            } else {
                binding.layoutNoitem.visibility = View.GONE
                it.patient.let{
                    closePatientListAdapter.submitList(it)
                }

            }
        })
        viewModel.changeStatus.observe(requireActivity(), {
            dismissLoading()
            snackBar("${it.message}", binding.root)
        })
        viewModel.getErrorMessage.observe(requireActivity(), {
            dismissLoading()
            snackBar("${it}", binding.root)
        })
    }
}

