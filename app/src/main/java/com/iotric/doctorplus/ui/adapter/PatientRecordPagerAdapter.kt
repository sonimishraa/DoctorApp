package com.iotric.doctorplus.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iotric.doctorplus.ui.fragment.LabReportFragment
import com.iotric.doctorplus.ui.fragment.PrescriptionFragment

class PatientRecordPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
       return when (position) {
            0 -> PrescriptionFragment()

            1-> LabReportFragment()
           else ->  PrescriptionFragment()
       }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val tabPageTitle = arrayOf("PRESCRIPTIONS","LABREPORTS")
        return tabPageTitle[position]
    }

}
