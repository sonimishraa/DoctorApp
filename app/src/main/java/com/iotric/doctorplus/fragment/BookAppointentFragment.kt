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
import com.iotric.doctorplus.databinding.BookAppointentFragmentBinding
import com.iotric.doctorplus.model.request.AddNewAppointmentRequest
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.BookAppointentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAppointentFragment : BaseFragment() {

    var hr = 0
    var min = 0
    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0

    val viewModel: BookAppointentViewModel by viewModels()
    val args: BookAppointentFragmentArgs by navArgs()
    lateinit var binding:BookAppointentFragmentBinding
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var visitDate: String
    lateinit var visitTime:String
    lateinit var description:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =BookAppointentFragmentBinding.inflate(layoutInflater)
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
        binding.appbar.toolbarTitle.text = "NEW APPOINTMENT"
    }


    private fun initListener() {
        binding.appbar.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.editNextAppointmentTime.setOnClickListener {
            pickAppointmentTime()
        }
        binding.editNextAppointmentDate.setOnClickListener {
            pickDate()
        }
        binding.btnCancle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSave.setOnClickListener {
            addnewAppointment()
        }

    }

    private fun addnewAppointment() {
        val patientId = args.patientId.id
        if (validateFields()){
            patientId?.let {
                val request =  AddNewAppointmentRequest(patientid = it, nextvisitdate = visitDate,
                    nextvisittime = visitTime,description = description)
                viewModel.getNewAppointmentApi(request,requireActivity().application)
            }
        }
    }
    private fun initObserver() {
      viewModel.newAppointment.observe(requireActivity(),{
          toastMessage("${it.message}")
          findNavController().popBackStack()

      })
        viewModel.getErrorMessage.observe(requireActivity(), {
            toastMessage("${it}")
        })
    }


    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        visitDate = binding.editNextAppointmentDate.text.toString()
        visitTime = binding.editNextAppointmentTime.text.toString()
        description = binding.editDiscription.text.toString()

        if (visitDate.isEmpty()) {
            binding.layoutEditNextAppointmentDate.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditNextAppointmentDate.setError(null)
        }
        if (visitTime.isEmpty()) {
            binding.layoutEditNextAppointmentTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditNextAppointmentTime.setError(null)
        }

        if (description.isEmpty()) {
            binding.layoutEditDiscrip.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditDiscrip.setError(null)
        }
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
                    binding.editNextAppointmentTime.setText(time)
                }
            }, hr, min, false)
        timePickerDialog.show()
    }

    private fun pickDate() {
        UtilClass.getDateCalendarInstance()
        datePickerDialog =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    pickYear = year
                    pickMonth = month + 1
                    pickDay = dayOfMonth
                    val date = UtilClass.makeDateString(pickYear, pickMonth, pickDay)
                    binding.editNextAppointmentDate.setText(date)
                }
            }, UtilClass.year, UtilClass.month, UtilClass.day)
        datePickerDialog.show()
    }

}