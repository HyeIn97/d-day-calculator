package com.example.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DayBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmController: DayAlarmController

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val update = Intent().apply {
                putExtra("update_day", true)
            }

            alarmController.startAlarm(update)
        }
    }
}