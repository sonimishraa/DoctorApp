package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatinetListAdapter
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel

class PatientListFragment : BaseFragment() {

    lateinit var viewModel: AddPatientViewModel
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
        initiviewModel()
        //initViewModel()
        //initObserver()
    }

    private fun initiviewModel() {
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)
        viewModel.allUser.observe(viewLifecycleOwner, {
            patientListAdapter.submitList(it)

        })
    }

    /* private fun initObserver() {
         viewModel.patientListLiveData.observe(viewLifecycleOwner, {
             patientListAdapter.submitList(it)

         })
         viewModel.setRequest()
     }

     private fun initViewModel() {
         viewModel =
             ViewModelProviders.of(this).get(PatientListViewModel::class.java)

     }*/

    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        patientListAdapter = PatinetListAdapter(object : PatinetListAdapter.ItemClickListener {
            override fun onItemLayoutClick(user: User) {
                val action = PatientListFragmentDirections.actionUpdatePatientFragment(user)
                findNavController().navigate(action)
            }

            override fun onDeleteClick(user: User) {
                viewModel.deleteUser(user)
            }

        })
        recyclerView.adapter = patientListAdapter
    }

}