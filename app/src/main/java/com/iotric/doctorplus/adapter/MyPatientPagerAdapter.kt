package com.iotric.doctorplus.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iotric.doctorplus.fragment.CloseCasePatientList
import com.iotric.doctorplus.fragment.ActivePatientListFragment

class MyPatientPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ActivePatientListFragment()
            1 -> CloseCasePatientList()
            else -> ActivePatientListFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val getPageTitle = arrayOf("ACTIVE PATIENTS", "INACTIVE PATIENTS")
        return getPageTitle[position]
    }
}
