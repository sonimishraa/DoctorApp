package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
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
import java.util.*

@AndroidEntryPoint
class UpdateAppointmentFragment : BaseFragment() {

    var hr = 0
    var min = 0
    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0
    var year= 0
    var month = 0
    var day = 0

    val viewModel: DailyAppointmentViewModel by viewModels()
    lateinit var binding: UpdateAppointmentFragmentBinding
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var datePickerDialog: DatePickerDialog
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
        binding.visitDate.setOnClickListener {
            pickDate()
        }
        binding.visitTime.setOnClickListener {
            pickAppointmentTime()
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

    private fun pickAppointmentTime() {
        UtilClass.getTimeCalender()

        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr = hourOfDay
                    min = minute
                    Log.i("start time", "$hourOfDay: $minute")
                    val time = UtilClass.time(hr, min)
                    binding.visitTime.setText(time)
                }
            }, hr, min, false)
        timePickerDialog.show()
    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        val now = calendar.timeInMillis
        datePickerDialog =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    pickYear = year
                    pickMonth = month + 1
                    pickDay = dayOfMonth
                    val date = UtilClass.makeDateString(pickYear, pickMonth, pickDay)
                    binding.visitDate.setText(date)
                }
            }, year, month, day)
        datePickerDialog.datePicker.minDate = now
        datePickerDialog.show()
    }




}