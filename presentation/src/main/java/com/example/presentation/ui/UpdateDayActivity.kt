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
class UpdateDayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayBinding
    private val viewModel: DayViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private var dayModel: DayModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        val updateDay = intent.getSerializableExtra("data", DayModel::class.java)

        updateDay?.let {
            dayModel = it
            initView(it)
            initListener()
        }

        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.notificationCount.collect {
                    it?.let {
                        updateDay(it, dayModel?.key ?: 0)
                    }
                }
            }

            launch {
                viewModel.updateDay.collect {
                    it?.let {
                        if (isServiceRunning(this@UpdateDayActivity, DayForegroundService::class.java)) {
                            val intent = Intent(this@UpdateDayActivity, DayForegroundService::class.java).apply {
                                putExtra("day", dayModel!!)
                            }

                            startService(intent)
                        } else {
                            startForegroundService(Intent(this@UpdateDayActivity, DayForegroundService::class.java))
                        }

                        finish()
                    }
                }
            }
        }
    }

    private fun initView(dayItem: DayModel) = with(binding) {
        titleEdt.setText(dayItem.title)
        val selectDay = dayItem.endDay.split("-")
        daySpinner.updateDate(selectDay[0].toInt(), (selectDay[1].toInt()) - 1, selectDay[2].toInt())
        notification.isChecked = dayItem.isNotification
        setting.isChecked = dayItem.isInclude
    }

    private fun initListener() = with(binding) {
        saveBtn.setOnClickListener {
            viewModel.getNotificationCount()
        }
    }

    private fun updateDay(isNotificationPossible: Boolean, key: Int) = with(binding) {
        if (isNotificationPossible) {
            if (titleEdt.text.toString().isBlank()) {
                Toast.makeText(this@UpdateDayActivity, getString(R.string.empty_title), Toast.LENGTH_SHORT).show()
            } else {
                val insertDay = if (!setting.isChecked) {
                    dateFormat.format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 1)
                    }.time)
                } else {
                    dateFormat.format(Calendar.getInstance().time)
                }
                val endDay = daySpinner.year.toString() + "-" + (daySpinner.month + 1) + "-" + daySpinner.dayOfMonth
                dayModel = DayModel(key, titleEdt.text.toString(), insertDay, endDay, notification.isChecked, setting.isChecked)
                viewModel.updateDay(dayModel!!)
            }
        } else {
            impossibilityDialog()
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