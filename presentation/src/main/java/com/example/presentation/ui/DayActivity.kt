package com.example.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.DayModel
import com.example.presentation.R
import com.example.presentation.databinding.ActivityDayBinding
import com.example.presentation.service.DayForegroundService
import com.example.presentation.viewmodel.DayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class DayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayBinding
    private val viewModel: DayViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private var notificationID = 1001
    private val dayService: DayForegroundService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDayBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        initListener()
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.insertDay.collect {
                    it?.let {
                        finish()
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun initListener() = with(binding) {
        saveBtn.setOnClickListener {
            if (titleEdt.text.toString().isBlank()) {
                Toast.makeText(this@DayActivity, getString(R.string.empty_title), Toast.LENGTH_SHORT).show()
            } else {
                val startDay = if (!setting.isChecked) {
                    dateFormat.format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 1)
                    }.time)
                } else {
                    dateFormat.format(Calendar.getInstance().time)
                }
                val endDay = daySpinner.year.toString() + "-" + (daySpinner.month + 1) + "-" + daySpinner.dayOfMonth
                viewModel.insertDay(DayModel(titleEdt.text.toString(), startDay, endDay, widget.isChecked, setting.isChecked))
            }
        }
    }
}