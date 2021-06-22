package com.iotric.doctorplus.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.SplashActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity:AppCompatActivity() {

    lateinit var sharePref: SharedPreferences

    lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        sharePref = getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val token = sharePref.getString("authToken", "")
        if(token != null){
            Handler().postDelayed({
                val mIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(mIntent)
                finish()
            }, 200)
        }else
            Handler().postDelayed({
                val mIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(mIntent)
                finish()
            }, 200)

    }

}