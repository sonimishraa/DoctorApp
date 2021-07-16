package com.iotric.doctorplus.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.WeeklyAppointmentAdapter
import com.iotric.doctorplus.databinding.WeeklyAppointmentFragmentBinding
import com.iotric.doctorplus.model.response.DataItem
import com.iotric.doctorplus.viewmodel.DailyAppointmentViewModel
import com.iotric.doctorplus.viewmodel.WeeklyAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeeklyAppointmentFragment : BaseFragment() {

    val viewModel: WeeklyAppointmentViewModel by viewModels()
    lateinit var binding: WeeklyAppointmentFragmentBinding
    lateinit var adapter: WeeklyAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeeklyAppointmentFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        adapter = WeeklyAppointmentAdapter(object : WeeklyAppointmentAdapter.ItemClickListener {
            override fun onUpadetAppointClick(item: DataItem) {
                val action =
                    AppointmentFragmentDirections.actionNavigationAppointmentToUpdateAppointmentFragment(
                        item
                    )
                findNavController().navigate(action)

            }

            override fun onDeleteAppointClick(item: DataItem) {
                showDeleteDilogue(item)
            }
        })
        binding.recyclerView.adapter = adapter
        viewModel.getAppointmentApi(requireActivity().application)
    }

    private fun initObserver() {
        showLoading()
        viewModel.getWeeklyAppoint.observe(requireActivity(), Observer {
            dismissLoading()
            if (it.data?.size == 0){
                binding.layoutNoitem.visibility =View.VISIBLE
            }
            else {
                dismissLoading()
                binding.layoutNoitem.visibility = View.GONE
                it?.let {
                    adapter.submitList(it.data)
                }
            }
        })
        viewModel.deleteAppointment.observe(requireActivity(), {
            dismissLoading()
            snackBar("${it.message}", binding.root)
        })

    }

    private fun showDeleteDilogue(item: DataItem) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.delete_appoint_dialogue, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val tv_cancel = dialogeView.findViewById<AppCompatTextView>(R.id.tv_cancel)
        val tv_ok = dialogeView.findViewById<AppCompatTextView>(R.id.tv_ok)

        tv_ok.setOnClickListener {
            item.id?.let {
                viewModel.deleteAppointApi(requireActivity().application, it)
                alertDialoge.dismiss()
            }
        }
        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
    }
}