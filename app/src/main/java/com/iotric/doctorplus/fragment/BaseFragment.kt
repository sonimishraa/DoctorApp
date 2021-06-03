package com.iotric.doctorplus.fragment

import androidx.fragment.app.Fragment
import com.iotric.doctorplus.activity.HomeActivity

abstract class BaseFragment : Fragment() {

    fun setToolbarTitle(title: String) {
        val activity = activity as HomeActivity
        activity.toolbar.title = ""
        activity.toolbarTitle.text = title
        activity.setSupportActionBar(activity.toolbar)
        activity.supportActionBar?.setHomeButtonEnabled(true)
    }
}