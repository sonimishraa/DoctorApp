package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatinetListAdapter
import com.iotric.doctorplus.databinding.PatientListFragmentBinding
import com.iotric.doctorplus.model.ResultsItem
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientListFragment : BaseFragment() {

    lateinit var viewModel: AddPatientViewModel
    lateinit var patientListAdapter: PatinetListAdapter
    private lateinit var binding:PatientListFragmentBinding

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
        setToolbarTitle(getString(R.string.patient_list))
        initView()
       // initiviewModel()
    }

    private fun initiviewModel() {
        viewModel.userResponse.observe(requireActivity(), {
           patientListAdapter.submitList(it.results)

        })
    }


    private fun initView() {
        patientListAdapter = PatinetListAdapter(object : PatinetListAdapter.ItemClickListener {
            override fun onItemLayoutClick(result: ResultsItem) {
                val action = PatientListFragmentDirections.actionUpdatePatientFragment()
                findNavController().navigate(action)
            }

            override fun onDeleteClick(result: ResultsItem) {
                //viewModel.deleteUser(user)
            }

        })
        binding.recyclerView.adapter = patientListAdapter
    }

}