package com.example.d_day_calculator.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.d_day_calculator.databinding.ActivityDayBinding
import com.example.d_day_calculator.service.DayForegroundService

class DayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayBinding
    private var notificationID = 1001
    private val dayService: DayForegroundService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDayBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        initListener()

    }

    private fun initListener() {
        binding.saveBtn.setOnClickListener {
            ++notificationID

            startForegroundService(Intent(this, DayForegroundService::class.java))

        }
    }
}