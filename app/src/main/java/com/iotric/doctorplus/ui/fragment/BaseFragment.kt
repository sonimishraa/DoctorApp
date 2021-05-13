package com.iotric.doctorplus.ui.fragment

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.iotric.doctorplus.HomeActivity
import com.iotric.doctorplus.R

abstract class BaseFragment : Fragment() {

    fun setToolbarTitle(title: String) {
        val activity = activity as HomeActivity
        activity.toolbar.title = ""
        activity.toolbarTitle.text = title
        activity.setSupportActionBar(activity.toolbar)
        activity.supportActionBar?.setHomeButtonEnabled(true)
    }

}