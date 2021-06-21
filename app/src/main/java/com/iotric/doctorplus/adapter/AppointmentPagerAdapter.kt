package com.iotric.doctorplus.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iotric.doctorplus.fragment.DailyAppointmentFragment
import com.iotric.doctorplus.fragment.WeeklyAppointmentFragment

class AppointmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DailyAppointmentFragment()

            1 -> WeeklyAppointmentFragment()

            else -> DailyAppointmentFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val getPageTitle = arrayOf("TODAY", "WEEK")
        return getPageTitle[position]
    }
}
