package com.iotric.doctorplus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        initView()
    }
    private fun initView() {
        val btnsignIn =findViewById<Button>(R.id.btnSignIn)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        btnsignIn?.setOnClickListener(this)
        setActionBar(toolbar)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignIn -> {
               val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }

}