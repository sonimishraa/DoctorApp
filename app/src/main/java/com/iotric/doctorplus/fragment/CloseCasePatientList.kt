package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.iotric.doctorplus.adapter.InActivePatientListAdapter
import com.iotric.doctorplus.databinding.ClosePatientListBinding
import com.iotric.doctorplus.model.response.Patient
import com.iotric.doctorplus.viewmodel.InActivePatientViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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
    }

    private fun initView() {
        viewModel.getClosePatientApi(requireActivity().application)
        closePatientListAdapter = InActivePatientListAdapter(object : InActivePatientListAdapter.ItemClickListener {
            override fun onChangeStatus(result: Patient) {
                val id = result.id
                if (id != null) {
                    viewModel.getStatusChangeApi(requireActivity().application, id)
                }
            }
        })
        binding.recyclerView.adapter = closePatientListAdapter
    }

    private fun initObserver() {
        showLoading()
        viewModel.closePatientList.observe(requireActivity(), {
            dismissLoading()
            val list = ArrayList<Patient>()
            it.patient?.let {
                list.add(it)
            }
            closePatientListAdapter.submitList(list)
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

