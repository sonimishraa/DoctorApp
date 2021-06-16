package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Delete
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatinetListAdapter
import com.iotric.doctorplus.databinding.PatientListFragmentBinding
import com.iotric.doctorplus.model.response.PatientsItem
import com.iotric.doctorplus.viewmodel.PatientListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientListFragment : BaseFragment() {

    val viewModel: PatientListViewModel by viewModels()
    lateinit var patientListAdapter: PatinetListAdapter
    private lateinit var binding: PatientListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PatientListFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initiviewModel()
    }

    private fun initiviewModel() {
        viewModel.allUserList.observe(requireActivity(), {
            patientListAdapter.submitList(it.patients)
        })

        viewModel.deletePatient.observe(requireActivity(),{
            it.message?.let{
                snackBar(it, binding.root)
            }
        })

    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_list)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        viewModel.getApiResponse(requireActivity().application)
        patientListAdapter = PatinetListAdapter(object : PatinetListAdapter.ItemClickListener {
            override fun onItemLayoutClick(result: PatientsItem) {
                val action = PatientListFragmentDirections.actionUpdatePatientFragment()
                findNavController().navigate(action)
            }

            override fun onDeleteClick(result: PatientsItem) {
                val id = result.id
                if (id != null) {
                    viewModel.getDeleteApiResponse(requireActivity().application, id)
                }
            }
        })
        binding.recyclerView.adapter = patientListAdapter
    }

}