package com.example.roomwordapp.ui.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.example.roomwordapp.MainActivity
import com.example.roomwordapp.R
import com.example.roomwordapp.ui.login.LoginActivity


class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: OnboardingPagerAdapter
    private lateinit var btnSkip: Button
    private lateinit var btnNext: Button
    private val layouts = intArrayOf(R.layout.onboarding_screen1, R.layout.onboarding_screen2, R.layout.onboarding_screen3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        btnSkip = findViewById(R.id.btnSkip)
        btnNext = findViewById(R.id.btnNext)

        adapter = OnboardingPagerAdapter(this, layouts.toList())
        viewPager.adapter = adapter

        btnSkip.setOnClickListener {
            launchLoginScreen()
        }

        btnNext.setOnClickListener {
            val current = viewPager.currentItem
            if (current < layouts.size - 1) {
                viewPager.currentItem = current + 1
            } else {
                launchLoginScreen()
            }
        }

        val isLoggedIn = checkLoginStatus()

        if (isLoggedIn) {
            // User is already logged in, navigate to the dashboard
            launchHomeScreen()
        }
    }
    private fun checkLoginStatus(): Boolean {
        // Retrieve the login status from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
    private fun launchLoginScreen() {
        // Add code to navigate to the home screen or the next activity after onboarding
        // For simplicity, we'll just finish this activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun launchHomeScreen() {
        // Add code to navigate to the home screen or the next activity after onboarding
        // For simplicity, we'll just finish this activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}