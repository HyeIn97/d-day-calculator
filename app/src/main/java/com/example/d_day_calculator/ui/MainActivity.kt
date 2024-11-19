package com.example.d_day_calculator.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.d_day_calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() = with(binding) {
        addBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, DayActivity::class.java))
        }
    }
}