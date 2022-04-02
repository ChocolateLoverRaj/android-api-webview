package com.chocolateloverraj.androidapiwebview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.chocolateloverraj.androidapiwebview.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        NavigationUI.setupWithNavController(binding!!.navView, navController)
    }
}