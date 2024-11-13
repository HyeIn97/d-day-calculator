package com.example.d_day_calculator

import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.apply {
            statusBarColor = this@SplashActivity.getColor(R.color.citrineWhite)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
            setDecorFitsSystemWindows(false)
            insetsController?.hide(WindowInsets.Type.navigationBars())
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (!hasFocus) return
        window.apply {
            setDecorFitsSystemWindows(false)
            insetsController?.hide(WindowInsets.Type.navigationBars())
        }
    }
}