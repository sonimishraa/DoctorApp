package com.iotric.doctorplus.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iotric.doctorplus.activity.HomeActivity


abstract class BaseFragments : Fragment() {

    fun setToolbarTitle(title: String) {
        val activity = activity as HomeActivity
        activity.toolbar.title = ""
        activity.toolbarTitle.text = title
        (activity as AppCompatActivity).setSupportActionBar(activity.toolbar)

    }
}