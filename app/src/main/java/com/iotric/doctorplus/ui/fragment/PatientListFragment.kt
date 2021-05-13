package com.iotric.doctorplus.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.ui.adapter.PatinetListAdapter
import com.iotric.doctorplus.ui.viewmodel.PatientListViewModel

class PatientListFragment : BaseFragment() {

    lateinit var viewModel: PatientListViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var patientListAdapter: PatinetListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.patient_list))
        initView(view)
        initViewModel()
        initObserver()
    }
    private fun initObserver() {
        viewModel.patientListLiveData.observe(viewLifecycleOwner, {
            patientListAdapter.submitList(it)

        })
        viewModel.setRequest()
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this).get(PatientListViewModel::class.java)

    }

    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        patientListAdapter = object : PatinetListAdapter() {
            override fun onItemLayoutClick(position: Int) {
            }

        }
        recyclerView.adapter = patientListAdapter
    }

}