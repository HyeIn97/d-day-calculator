package com.example.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.DayModel
import com.example.presentation.R
import com.example.presentation.databinding.ActivityDayBinding
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
    private val numberRange = (0..99999)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val updateDay = intent.getSerializableExtra("data", DayModel::class.java)

        binding = ActivityDayBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        updateDay?.let {
            initUpdateView(it)
            initUpdateListener(it.key)
        } ?: run {
            initInsertListener()
        }
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.insertDay.collect {
                    finish()
                }
            }

            launch {
                viewModel.updateDay.collect {
                    finish()
                }
            }
        }
    }

    private fun initInsertListener() = with(binding) {
        saveBtn.setOnClickListener {
            if (titleEdt.text.toString().isBlank()) {
                Toast.makeText(this@DayActivity, getString(R.string.empty_title), Toast.LENGTH_SHORT).show()
            } else {
                val insertDay = if (!setting.isChecked) {
                    dateFormat.format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 1)
                    }.time)
                } else {
                    dateFormat.format(Calendar.getInstance().time)
                }
                val endDay = daySpinner.year.toString() + "-" + (daySpinner.month + 1) + "-" + daySpinner.dayOfMonth
                viewModel.insertDay(DayModel(numberRange.random(), titleEdt.text.toString(), insertDay, endDay, widget.isChecked, setting.isChecked))
            }
        }
    }

    private fun initUpdateView(dayItem: DayModel) = with(binding) {
        titleEdt.setText(dayItem.title)
        val selectDay = dayItem.endDay.split("-")
        daySpinner.updateDate(selectDay[0].toInt(), (selectDay[1].toInt()) - 1, selectDay[2].toInt())
        widget.isChecked = dayItem.isWidget
        setting.isChecked = dayItem.isInclude
    }

    private fun initUpdateListener(key: Int) = with(binding) {
        saveBtn.setOnClickListener {
            if (titleEdt.text.toString().isBlank()) {
                Toast.makeText(this@DayActivity, getString(R.string.empty_title), Toast.LENGTH_SHORT).show()
            } else {
                val insertDay = if (!setting.isChecked) {
                    dateFormat.format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 1)
                    }.time)
                } else {
                    dateFormat.format(Calendar.getInstance().time)
                }
                val endDay = daySpinner.year.toString() + "-" + (daySpinner.month + 1) + "-" + daySpinner.dayOfMonth
                viewModel.updateDay(DayModel(key, titleEdt.text.toString(), insertDay, endDay, widget.isChecked, setting.isChecked))
            }
        }
    }
}