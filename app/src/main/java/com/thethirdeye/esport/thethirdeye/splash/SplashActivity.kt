package com.thethirdeye.esport.thethirdeye.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thethirdeye.esport.thethirdeye.R
import com.thethirdeye.esport.thethirdeye.auth.LoginActivity
import com.thethirdeye.esport.thethirdeye.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        startSplashAnimation()
        renderSplashScreen()
    }

    private fun startSplashAnimation() {
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.scale_fade)
        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val subtitleAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.ivLogo.startAnimation(logoAnim)
        binding.tvAppName.startAnimation(titleAnim)
        binding.tvTagline.startAnimation(subtitleAnim)
    }

    private fun renderSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2100)
    }
}