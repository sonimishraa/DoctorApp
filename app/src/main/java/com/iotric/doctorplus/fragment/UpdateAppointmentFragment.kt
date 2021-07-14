package com.iotric.doctorplus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.UpdateAppointmentFragmentBinding
import com.iotric.doctorplus.model.request.UpdateAppointmentRequest
import com.iotric.doctorplus.util.DateTimeUtil
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.DailyAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateAppointmentFragment : BaseFragment() {

    val viewModel: DailyAppointmentViewModel by viewModels()
    lateinit var binding: UpdateAppointmentFragmentBinding
    val args: UpdateAppointmentFragmentArgs by navArgs()
    lateinit var visitDate:String
    lateinit var visitTime:String
    lateinit var description:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UpdateAppointmentFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        val visitdate = args.AppointItem.nextvisitdate
        binding.visitDate.setText(DateTimeUtil.getSimpleDateFromUtc(visitdate))
        binding.visitTime.setText(args.AppointItem.nextvisittime)
        binding.description.setText(args.AppointItem.description)
    }

    private fun initListener() {
        binding.btnUpdate.setOnClickListener {
            updateAppoint()
        }
    }

    private fun updateAppoint() {
        if(validateFields()) {
            val appointId = args.AppointItem.id
            if (appointId != null) {
                val updateRequest = UpdateAppointmentRequest(
                    nextvisitdate = visitDate,
                    nextvisittime = visitTime,
                    description = description
                )
                viewModel.updateAppointmentApi(
                    appointId,
                    updateRequest,
                    requireActivity().application
                )
            }
        }
    }

    private fun initObserver() {
        viewModel.updateAppointment.observe(requireActivity(),{
            toastMessage("${it.message}")
            findNavController().popBackStack()
        })
        viewModel.deleteAppointment.observe(requireActivity(),{
            snackBar("${it.message}",binding.root)
        })
        viewModel.getErrorMessage.observe(requireActivity(),{
            toastMessage("${it}")
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        visitDate = binding.visitDate.text.toString()
        visitTime = binding.visitTime.text.toString()
        description = binding.description.text.toString()


        if (visitDate.isEmpty()) {
            binding.layoutVisitDate.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutVisitDate.setError(null)
        }

        if (visitTime.isEmpty()) {
            binding.layoutVisitTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutVisitTime.setError(null)

        if (description.isEmpty()) {
            binding.layoutDescription.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutDescription.setError(null)

        return isAllFieldValidate
    }



}