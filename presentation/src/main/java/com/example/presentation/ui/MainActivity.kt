package com.example.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.databinding.ActivityMainBinding

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
            startActivity(Intent(this@MainActivity, com.example.presentation.ui.DayActivity::class.java))
        }
    }
}