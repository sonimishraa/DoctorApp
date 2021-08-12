package com.iotric.doctorplus.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

// Not in USE wright now

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
   private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val sharedPreferences =
            getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val authToken = sharedPreferences.getString("authToken", "")
        val id = sharedPreferences.getString("_id", "")
        Log.i("authToken", "${authToken}")
        Log.i("_id", "${id}")
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_lab, R.id.navigation_notifications -> showBottomNav()
                R.id.navigation_dashboard -> hideBottomNav()
                else -> hideBottomNav()
            }
        }
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }
}