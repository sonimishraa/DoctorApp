package com.iotric.doctorplus.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iotric.doctorplus.ui.fragment.BlogsFragment
import com.iotric.doctorplus.ui.fragment.HomeFragment
import com.iotric.doctorplus.ui.fragment.NewsFeedFragment

class HomeFragmentPagerAdapter( fm:FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
       return when (position) {
            0-> BlogsFragment()

           1-> NewsFeedFragment()

           else -> BlogsFragment()
       }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val getPageTitle = arrayOf("Blogs","NewsFeeds")
        return getPageTitle[position]
    }
}
