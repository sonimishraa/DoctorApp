package com.iotric.doctorplus.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.AppointmentPagerAdapter
import com.iotric.doctorplus.adapter.HomeFragmentPagerAdapter
import com.iotric.doctorplus.databinding.FragmentAppointentBinding
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.viewmodel.AppointmentFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    var year = 0
    var month = 0
    var day = 0

    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0

    lateinit var datePickerDialog: DatePickerDialog
    private lateinit var binding: FragmentAppointentBinding

    val viewModel: AppointmentFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
       // pickDate()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.appointment_toolbar_title)
        binding.viewPager.adapter = AppointmentPagerAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
        binding.tablayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFF00"))
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
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
                   // binding.tvAppointmentDate.text = date
                }

            }, year, month, day)
        datePickerDialog.show()
    }

}