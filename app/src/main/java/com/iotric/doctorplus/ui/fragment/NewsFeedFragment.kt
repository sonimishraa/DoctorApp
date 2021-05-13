package com.iotric.doctorplus.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iotric.doctorplus.R

class NewsFeedFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_newsfeed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val webView = view.findViewById<WebView>(R.id.webView)
           webView.loadUrl("https://www.google.com/")
    }
}