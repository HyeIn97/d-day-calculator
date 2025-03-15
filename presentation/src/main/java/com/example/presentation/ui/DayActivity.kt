package com.example.presentation.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
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
import com.example.presentation.common.CustomDialog
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
    private val numberRange = (0..99999)
    private var test: DayModel? = null

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

        viewModel.getNotificationCount()
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.insertDay.collect {
                    it?.let {
                        if (isServiceRunning(this@DayActivity, DayForegroundService::class.java)) {
                            val intent = Intent(this@DayActivity, DayForegroundService::class.java).apply {
                                putExtra("day", test!!)
                            }

                            startService(intent)
                        } else {
                            startForegroundService(Intent(this@DayActivity, DayForegroundService::class.java))
                        }

                        finish()
                    }
                }
            }

            launch {
                viewModel.updateDay.collect {
                    it?.let {
                        if (isServiceRunning(this@DayActivity, DayForegroundService::class.java)) {
                            val intent = Intent(this@DayActivity, DayForegroundService::class.java).apply {
                                putExtra("day", test!!)
                            }

                            startService(intent)
                        } else {
                            startForegroundService(Intent(this@DayActivity, DayForegroundService::class.java))
                        }

                        finish()
                    }
                }
            }
        }
    }

    private fun initInsertListener() = with(binding) {
        notification.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!viewModel.isNotificationPossible && isChecked) {
                compoundButton.isChecked = false
                impossibilityDialog()
            }
        }

        saveBtn.setOnClickListener {
            if (viewModel.isNotificationPossible) {
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
                    test = DayModel(numberRange.random(), titleEdt.text.toString(), insertDay, endDay, notification.isChecked, setting.isChecked)
                    viewModel.insertDay(test!!)
                }
            } else {
                impossibilityDialog()
            }
        }
    }

    private fun initUpdateView(dayItem: DayModel) = with(binding) {
        titleEdt.setText(dayItem.title)
        val selectDay = dayItem.endDay.split("-")
        daySpinner.updateDate(selectDay[0].toInt(), (selectDay[1].toInt()) - 1, selectDay[2].toInt())
        notification.isChecked = dayItem.isNotification
        setting.isChecked = dayItem.isInclude
    }

    private fun initUpdateListener(key: Int) = with(binding) {
        saveBtn.setOnClickListener {
            if (viewModel.isNotificationPossible) {
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
                    test = DayModel(key, titleEdt.text.toString(), insertDay, endDay, notification.isChecked, setting.isChecked)
                    viewModel.updateDay(test!!)
                }
            } else {
                impossibilityDialog()
            }
        }
    }

    private fun impossibilityDialog() = CustomDialog().Builder().apply {
        setIsSingleBtn(true)
        setContentTxt(getString(R.string.impossibility_content))
        setPositiveTxt(getString(R.string.done))
    }.show(supportFragmentManager, "deleteDialog")

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}