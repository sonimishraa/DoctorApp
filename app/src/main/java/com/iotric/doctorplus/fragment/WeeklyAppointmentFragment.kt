package com.iotric.doctorplus.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iotric.doctorplus.R
import com.iotric.doctorplus.viewmodel.WeeklyAppointmentViewModel

class WeeklyAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = WeeklyAppointmentFragment()
    }

    private lateinit var viewModel: WeeklyAppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weekly_appointment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeeklyAppointmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}