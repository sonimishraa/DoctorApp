package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatinetListAdapter
import com.iotric.doctorplus.databinding.PatientListFragmentBinding
import com.iotric.doctorplus.model.response.PatientsItems
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
        initiObserver()
    }

    private fun initiObserver() {
        showLoading()
        viewModel.allUserList.observe(requireActivity(), {
            patientListAdapter.submitList(it.patients)
            dismissLoading()
        })

        viewModel.deletePatient.observe(requireActivity(),{
            it.message?.let{
                snackBar(it, binding.root)
                dismissLoading()
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
            override fun onItemLayoutClick(result: PatientsItems) {
                val action = PatientListFragmentDirections.actionUpdatePatientFragment(result)
                findNavController().navigate(action)
            }

            override fun onDeleteClick(result: PatientsItems) {
                showDeleteDilogue(result)
            }
        })
        binding.recyclerView.adapter = patientListAdapter
    }

    private fun showDeleteDilogue(result:PatientsItems) {
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
            Log.i("PatientListFragment","id: ${result.id}")
            result.id?.let {
                viewModel.getDeleteApiResponse(requireActivity().application, it)
                toastMessage("Patient Deleted")
                alertDialoge.dismiss()
            }
        }
        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
    }
}
