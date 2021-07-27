package com.iotric.doctorplus.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.SplashActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        Handler().postDelayed({
            val authToken = checkAuthtoken()
            Log.i("SplashActivity", "authToken:${authToken}")
            if (authToken.isNullOrEmpty()) {
                val mIntent = Intent( this@SplashActivity, LoginActivity::class.java)
                startActivity(mIntent)
                finish()
            }
            else {
                val intent = Intent(this@SplashActivity, HomeActivity1::class.java)
                startActivity(intent)
                finish()
            }

        }, 500)
    }

    private fun checkAuthtoken(): String? {
        val sharedPreferences =
            getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val authToken = sharedPreferences.getString("authToken", "")
        return authToken
    }

}