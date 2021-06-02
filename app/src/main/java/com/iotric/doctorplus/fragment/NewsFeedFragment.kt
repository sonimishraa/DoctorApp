package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentNewsfeedBinding
import com.iotric.doctorplus.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFeedFragment : BaseFragment() {
    private lateinit var binding: FragmentNewsfeedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      binding = FragmentNewsfeedBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
           binding.webView.loadUrl("https://www.google.com/")
    }
}