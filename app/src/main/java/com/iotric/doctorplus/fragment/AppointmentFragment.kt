package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProviders
import com.iotric.doctorplus.R
import com.iotric.doctorplus.viewmodel.AppointmentFragmentViewModel
import java.util.*


class AppointmentFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    var year = 0
    var month = 0
    var day = 0

    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0

    lateinit var datePicker: AppCompatTextView
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var viewModel: AppointmentFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.menu_appointment))
        initView(view)
        initViewModel()
        pickDate()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AppointmentFragmentViewModel::class.java)
    }

    private fun pickDate() {
        getDateCalender()
        datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }

    private fun getDateCalender() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

    }

    private fun initView(view: View) {
        datePicker = view.findViewById(R.id.tv_appointmentDate)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pickYear = year
        pickMonth = month + 1
        pickDay = dayOfMonth
        val date = viewModel.makeDateString(pickYear, pickMonth, pickDay)
        datePicker.text = date
    }

}