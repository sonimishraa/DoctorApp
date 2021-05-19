package com.iotric.doctorplus.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iotric.doctorplus.R

open class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var navView: BottomNavigationView
    lateinit var menuBar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        menuBar = findViewById(R.id.menubar)
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
        navView.setupWithNavController(navController)
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
        navView.visibility = View.GONE
    }

    private fun showBottomNav() {
        navView.visibility = View.VISIBLE
    }

}