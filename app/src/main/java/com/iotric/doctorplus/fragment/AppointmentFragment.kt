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
import com.iotric.doctorplus.databinding.FragmentAppointentBinding
import com.iotric.doctorplus.viewmodel.AppointmentFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AppointmentFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    var year = 0
    var month = 0
    var day = 0

    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0
    private lateinit var binding:FragmentAppointentBinding

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var viewModel: AppointmentFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentAppointentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.menu_appointment))
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pickYear = year
        pickMonth = month + 1
        pickDay = dayOfMonth
        val date = viewModel.makeDateString(pickYear, pickMonth, pickDay)
        binding.tvAppointmentDate.text = date
    }

}