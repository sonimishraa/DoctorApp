package com.iotric.doctorplus.activity

import android.os.Bundle
import com.iotric.doctorplus.databinding.ForgetPasswordActivityBinding
import com.iotric.doctorplus.fragment.BaseActivity

class ForgetPasswordActivity: BaseActivity() {

    lateinit var binding:ForgetPasswordActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgetPasswordActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initview()
    }

    private fun initview() {
    }
}