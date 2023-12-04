package com.example.roomwordapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.roomwordapp.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Start the main activity after the splash delay
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}