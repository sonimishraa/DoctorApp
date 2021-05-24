package com.iotric.doctorplus.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        toolbar = binding.toolbar
        toolbarTitle = binding.toolbarTitle
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_medical_records -> noBackpressIcon()
                else -> showBackPressIcon()
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_lab, R.id.navigation_notifications -> showBottomNav()
                R.id.navigation_medical_records -> hideBottomNav()
                else -> hideBottomNav()
            }
        }
        binding.navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun noBackpressIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun showBackPressIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

}