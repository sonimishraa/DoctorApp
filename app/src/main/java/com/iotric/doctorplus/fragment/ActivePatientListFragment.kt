package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatinetListAdapter
import com.iotric.doctorplus.databinding.PatientListFragmentBinding
import com.iotric.doctorplus.model.response.PatientsItem
import com.iotric.doctorplus.viewmodel.PatientListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivePatientListFragment : BaseFragment() {

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
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_list)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        viewModel.getActivePatientApiResponse(requireActivity().application)
        patientListAdapter = PatinetListAdapter(object : PatinetListAdapter.ItemClickListener {
            override fun onItemLayoutClick(result: PatientsItem) {
                val action = ActivePatientListFragmentDirections.actionPatientRecordsFragment(result)
                findNavController().navigate(action)
            }

            override fun onPatientProfileClick(result: PatientsItem) {
                val action = ActivePatientListFragmentDirections.actionPatientRecordsFragment(result)
                findNavController().navigate(action)
            }

            override fun onUpdateProfile(result: PatientsItem) {
                val action = ActivePatientListFragmentDirections.actionUpdatePatientFragment(result)
                findNavController().navigate(action)
            }

            override fun onChangeStatus(result: PatientsItem) {
                showChangeStatusDilogue(result)
            }

            override fun onBookAppointmentClick(result: PatientsItem) {
                val patientId = result
                val action = ActivePatientListFragmentDirections.actionPatientListFragementToBookAppointentFragment(patientId)
                findNavController().navigate(action)
            }

            override fun onDeleteClick(result: PatientsItem) {
                showDeleteDilogue(result)
            }

        })
        binding.recyclerView.adapter = patientListAdapter
    }

    private fun initObserver() {
        showLoading()
        viewModel.allUserList.observe(requireActivity(), {
            dismissLoading()
            if (it.patients.isNullOrEmpty()) {
                binding.layoutNoitem.visibility = View.VISIBLE
            } else {
                binding.layoutNoitem.visibility = View.GONE
                it.patients.let {
                    patientListAdapter.submitList(it)
                }
            }
        })

        viewModel.deletePatient.observe(requireActivity(), {
            dismissLoading()
            it.message?.let {
                snackBar(it, binding.root)
            }
        })
        viewModel.patientStatusChange.observe(requireActivity(), {
            dismissLoading()
            it.message?.let {
                snackBar(it, binding.root)
            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {
            dismissLoading()
            snackBar(it, binding.root)
        })
    }

    private fun showDeleteDilogue(result: PatientsItem) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.delete_dialogue, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val tv_cancel = dialogeView.findViewById<AppCompatTextView>(R.id.tv_cancel)
        val tv_ok = dialogeView.findViewById<AppCompatTextView>(R.id.tv_ok)

        tv_ok.setOnClickListener {
            Log.i("PatientListFragment", "id: ${result.id}")
            result.id?.let {
                viewModel.getDeleteApiResponse(requireActivity().application, it)
                alertDialoge.dismiss()
            }
        }
        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
    }

    private fun showChangeStatusDilogue(result: PatientsItem) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.change_status_dialogue, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val tv_cancel = dialogeView.findViewById<AppCompatTextView>(R.id.tv_cancel)
        val tv_ok = dialogeView.findViewById<AppCompatTextView>(R.id.tv_ok)

        tv_ok.setOnClickListener {
            Log.i("PatientListFragment", "id: ${result.id}")
            result.id?.let {
                viewModel.getStatusChangeApi(requireActivity().application, it)
                alertDialoge.dismiss()
            }
        }
        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
    }

}
