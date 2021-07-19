package com.iotric.doctorplus.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.iotric.doctorplus.databinding.ForgetPasswordActivityBinding
import com.iotric.doctorplus.fragment.BaseActivity
import com.iotric.doctorplus.viewmodel.ForgetPasswordViewModel

class ForgetPasswordActivity: BaseActivity() {

    lateinit var binding:ForgetPasswordActivityBinding
    val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgetPasswordActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initview()
        initObsever()
    }

    private fun initview() {
        binding.btForget.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initObsever() {

    }

}