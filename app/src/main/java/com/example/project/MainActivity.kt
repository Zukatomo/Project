package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)
        // inflate layout


        mNavController = (supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment).navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNav.setupWithNavController(mNavController)

    }
}