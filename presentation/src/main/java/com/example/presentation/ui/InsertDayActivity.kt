package com.example.presentation.ui

import android.app.Activity
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
import com.example.presentation.helper.NotificationHelper
import com.example.presentation.viewmodel.DayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class InsertDayActivity : AppCompatActivity() {
    @Inject
    lateinit var notificationHelper: NotificationHelper

    private lateinit var binding: ActivityDayBinding
    private val viewModel: DayViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val numberRange = (0..99999)
    private var dayModel: DayModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        initInsertListener()
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.notificationCount.collect {
                    insertDay(it)
                }
            }

            launch {
                viewModel.insertDay.collect {
                    it?.let {
                        if (binding.notification.isChecked) {
                            val intent = Intent(this@InsertDayActivity, NotificationHelper::class.java).apply {
                                putExtra("day", dayModel!!)
                            }

                            notificationHelper.createNotify(intent)
                        } else {
                            val intent = Intent(this@InsertDayActivity, NotificationHelper::class.java).apply {
                                putExtra("key", dayModel?.key ?: -200)
                            }

                            notificationHelper.removeNotify(intent)
                        }

                        val resultIntent = Intent().apply {
                            putExtra("insertDay", dayModel)
                        }

                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                }
            }
        }
    }

    private fun initInsertListener() = with(binding) {
        saveBtn.setOnClickListener {
            viewModel.getNotificationCount()
        }
    }

    private fun insertDay(isNotificationPossible: Boolean) = with(binding) {
        if (titleEdt.text.toString().isBlank()) {
            Toast.makeText(this@InsertDayActivity, getString(R.string.empty_title), Toast.LENGTH_SHORT).show()
        } else {
            if (!isNotificationPossible && notification.isChecked) {
                impossibilityDialog()
            } else {
                val insertDay = if (!setting.isChecked) {
                    dateFormat.format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 1)
                    }.time)
                } else {
                    dateFormat.format(Calendar.getInstance().time)
                }
                val endDay = daySpinner.year.toString() + "-" + (daySpinner.month + 1) + "-" + daySpinner.dayOfMonth
                dayModel = DayModel(numberRange.random(), titleEdt.text.toString(), insertDay, endDay, notification.isChecked, setting.isChecked)
                viewModel.insertDay(dayModel!!)
            }
        }
    }

    private fun impossibilityDialog() = CustomDialog().Builder().apply {
        setIsSingleBtn(true)
        setContentTxt(getString(R.string.impossibility_content))
        setPositiveTxt(getString(R.string.done))
    }.show(supportFragmentManager, "deleteDialog")
}