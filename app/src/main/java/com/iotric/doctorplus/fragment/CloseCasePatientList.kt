package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.adapter.InActivePatientListAdapter
import com.iotric.doctorplus.databinding.ClosePatientListBinding
import com.iotric.doctorplus.model.response.Patient
import com.iotric.doctorplus.viewmodel.InActivePatientViewModel
import com.iotric.doctorplus.viewmodel.PatientListViewModel
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
        binding.appbar.toolbarTitle.text = "INACTIVE PATIENT LIST"
        binding.appbar.toolbar.setOnClickListener{
            findNavController().popBackStack()
        }
        viewModel.getClosePatientApi(requireActivity().application)
        closePatientListAdapter =
            InActivePatientListAdapter(object : InActivePatientListAdapter.ItemClickListener {
                override fun onChangeStatus(result: Patient) {
                    val id = result.id
                    if (id != null) {
                        viewModel.getClosePatientApi(requireActivity().application)
                    }
                }
            })
        binding.recyclerView.adapter = closePatientListAdapter
    }

    private fun initObserver() {
        viewModel.closePatientList.observe(requireActivity(), {
            if (it.patient != null) {
                binding.layoutNoitem.visibility = View.GONE
                val list = ArrayList<Patient>()
                it.patient.let {
                    list.add(it)
                    closePatientListAdapter.submitList(list)
                }

            } else {
                binding.layoutNoitem.visibility = View.VISIBLE
            }
        })
        viewModel.changeStatus.observe(requireActivity(), {
            snackBar("${it.message}", binding.root)
        })
        viewModel.getErrorMessage.observe(requireActivity(), {
            snackBar("${it}", binding.root)
        })
    }
}

