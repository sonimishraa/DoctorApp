package com.iotric.doctorplus.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.iotric.doctorplus.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrDashboardActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dr_activity_dashbord)
        initView()
    }

    private fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer)
        val navView: NavigationView = findViewById(R.id.nav)
        val navController = findNavController(R.id.nav_host)
        val appbar = findViewById<AppBarLayout>(R.id.appbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        toolbar.navigationIcon?.setTint(getColor(R.color.white))
        appbar.setOutlineProvider(null)
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_logout,
                R.id.navigation_privacy, R.id.navigation_faq, R.id.navigation_terms,
                R.id.navigation_logout, R.id.navigation_notifications, R.id.navigation_lab
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}